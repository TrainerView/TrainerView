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

        with(binding.toolbar) {
            setNavigationOnClickListener {
                when (viewModel.uiState.value.selectedParticipantId) {
                    null -> findNavController().popBackStack()
                    else -> viewModel.clearParticipantSelection()
                }
            }
            applySystemInsetsTop()
        }
        binding.participantRv.adapter = viewModel.adapter
        binding.addParticipantButton.setOnClickListener {
            findNavController().navigate(
                UpdateGroupFragmentDirections.actionToAddParticipantFragment(
                    UpdateParticipantType.CreateParticipant
                )
            )
        }
        binding.deleteButton.setOnClickListener {
            viewModel.onDeleteButtonClick()
        }

        binding.editButton.setOnClickListener {
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
                binding.saveButton.setOnClickListener {
                    viewModel.createGroup(binding.nameInput.text.toString())
                    findNavController().popBackStack()
                }
            }
            is UpdateGroupType.EditGroup -> {
                binding.nameInput.setText(updateMode.groupName, TextView.BufferType.EDITABLE)
                binding.saveButton.setOnClickListener {
                    viewModel.updateGroup(updateMode.groupId, binding.nameInput.text.toString())
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
                binding.toolbar.setNavigationIcon(R.drawable.ic_back)
                binding.saveButton.isVisible = true
                binding.editButton.isVisible = false
                binding.deleteButton.isVisible = false
            }
            else -> {
                binding.toolbar.setNavigationIcon(R.drawable.ic_close)
                binding.saveButton.isVisible = false
                binding.editButton.isVisible = true
                binding.deleteButton.isVisible = true
            }
        }
    }
}