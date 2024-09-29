package com.trainerview.app.presentation.update_group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.trainerview.app.R
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.data.db.model.ParticipantDb
import com.trainerview.app.databinding.FragmentUpdateGroupBinding
import com.trainerview.app.presentation.add_participant.AddParticipantFragment.Companion.ADD_PARTICIPANT_MODEL_KEY
import com.trainerview.app.presentation.add_participant.AddParticipantFragment.Companion.ADD_PARTICIPANT_REQUEST_KEY
import com.trainerview.app.presentation.add_participant.CreateParticipantModel
import com.trainerview.app.presentation.add_participant.UpdateParticipantType
import com.trainerview.app.presentation.update_group.di.DaggerAddGroupComponent
import kotlinx.coroutines.launch

class UpdateGroupFragment : BaseFragment<FragmentUpdateGroupBinding, UpdateGroupViewModel>() {

    override val viewModel by injectViewModel<UpdateGroupViewModel>()
    override fun diComponent() = DaggerAddGroupComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    private val args: UpdateGroupFragmentArgs by navArgs()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateGroupBinding {
        return FragmentUpdateGroupBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (val updateMode = args.updateMode) {
            UpdateGroupType.CreateGroup -> Unit
            is UpdateGroupType.EditGroup -> {
                viewModel.load(updateMode.groupId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.fragmentAddGroupToolbar) {
            setNavigationOnClickListener {
                when (viewModel.uiState.value.selectedParticipantId) {
                    null -> findNavController().popBackStack()
                    else -> viewModel.clearParticipantSelection()
                }
            }
            applySystemInsetsTop()
        }
        binding.fragmentAddGroupParticipantRv.adapter = viewModel.adapter
        binding.fragmentAddGroupAddParticipant.setOnClickListener {
            findNavController().navigate(
                UpdateGroupFragmentDirections.actionToAddParticipantFragment(
                    UpdateParticipantType.CreateParticipant
                )
            )
        }
        binding.fragmentUpdateGroupDeleteButton.setOnClickListener {
            viewModel.onDeleteButtonClick()
        }

        binding.fragmentUpdateGroupEditButton.setOnClickListener {
            with(viewModel.uiState.value) {
                participants.firstOrNull { selectedParticipantId == it.id }?.let {
                    viewModel.clearParticipantSelection()
                    findNavController().navigate(
                        UpdateGroupFragmentDirections.actionToAddParticipantFragment(
                            UpdateParticipantType.EditParticipant(
                                participantId = it.id,
                                participantName = it.name
                            )
                        )
                    )
                }
            }
        }

        when (val updateMode = args.updateMode) {
            UpdateGroupType.CreateGroup -> {
                binding.fragmentUpdateGroupSaveButton.setOnClickListener {
                    viewModel.createGroup(binding.fragmentAddGroupNameInput.text.toString())
                    findNavController().popBackStack()
                }
            }
            is UpdateGroupType.EditGroup -> {
                binding.fragmentAddGroupNameInput.setText(updateMode.groupName, TextView.BufferType.EDITABLE)
                binding.fragmentUpdateGroupSaveButton.setOnClickListener {
                    viewModel.updateGroup(updateMode.groupId, binding.fragmentAddGroupNameInput.text.toString())
                    findNavController().popBackStack()
                }
            }
        }

        setFragmentResultListener(ADD_PARTICIPANT_REQUEST_KEY) { key, bundle ->
            bundle.getParcelable<CreateParticipantModel>(ADD_PARTICIPANT_MODEL_KEY)?.let(::onCreateParticipant)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateToolbar(uiState)
                }
            }
        }
    }

    private fun onCreateParticipant(participant: CreateParticipantModel) {
        viewModel.addParticipant(participant)
    }

    private fun updateToolbar(state: AddGroupScreenState) {
        when (state.selectedParticipantId) {
            null -> {
                binding.fragmentAddGroupToolbar.setNavigationIcon(R.drawable.ic_back)
                binding.fragmentUpdateGroupSaveButton.isVisible = true
                binding.fragmentUpdateGroupEditButton.isVisible = false
                binding.fragmentUpdateGroupDeleteButton.isVisible = false
            }
            else -> {
                binding.fragmentAddGroupToolbar.setNavigationIcon(R.drawable.ic_close)
                binding.fragmentUpdateGroupSaveButton.isVisible = false
                binding.fragmentUpdateGroupEditButton.isVisible = true
                binding.fragmentUpdateGroupDeleteButton.isVisible = true
            }
        }
    }
}