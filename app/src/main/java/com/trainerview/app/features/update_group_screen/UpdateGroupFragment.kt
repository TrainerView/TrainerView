package com.trainerview.app.features.update_group_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.trainerview.app.R
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.ui.BaseFragment
import com.trainerview.app.databinding.FragmentUpdateGroupBinding
import com.trainerview.app.features.update_group_screen.di.DaggerAddGroupComponent
import com.trainerview.app.features.update_participant_screen.UpdateParticipantFragment.Companion.ADD_PARTICIPANT_MODEL_KEY
import com.trainerview.app.features.update_participant_screen.UpdateParticipantFragment.Companion.ADD_PARTICIPANT_REQUEST_KEY
import com.trainerview.app.features.update_participant_screen.UpdateParticipantModel
import com.trainerview.app.features.update_participant_screen.UpdateParticipantNavParams
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

        when (val updateMode = args.updateParams) {
            UpdateGroupNavParams.CreateGroup -> Unit
            is UpdateGroupNavParams.EditGroup -> {
                viewModel.load(updateMode.groupId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeFragmentResult()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateToolbar(uiState)
                }
            }
        }
    }

    private fun subscribeFragmentResult() {
        setFragmentResultListener(ADD_PARTICIPANT_REQUEST_KEY) { key, bundle ->
            bundle.getParcelable<UpdateParticipantModel>(ADD_PARTICIPANT_MODEL_KEY)?.let {
                viewModel.addParticipant(it)
            }
        }
    }

    private fun initUI() {
        with(binding) {
            toolbar.setNavigationOnClickListener { viewModel.onToolbarNavIconClick() }
            toolbar.applySystemInsetsTop()
            participantRv.adapter = viewModel.adapter
            addParticipantButton.setOnClickListener {
                findNavController().navigate(
                    UpdateGroupFragmentDirections.actionToAddParticipantFragment(
                        UpdateParticipantNavParams.CreateParticipant
                    )
                )
            }
            deleteButton.setOnClickListener { viewModel.onDeleteButtonClick() }
            editButton.setOnClickListener { viewModel.onEditSelectedParticipantClick() }
            saveButton.setOnClickListener { viewModel.onSaveButtonClick(binding.nameInput.text.toString()) }

            when (val params = args.updateParams) {
                UpdateGroupNavParams.CreateGroup -> Unit
                is UpdateGroupNavParams.EditGroup -> {
                    nameInput.setText(params.groupName, TextView.BufferType.EDITABLE)
                }
            }
        }
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
