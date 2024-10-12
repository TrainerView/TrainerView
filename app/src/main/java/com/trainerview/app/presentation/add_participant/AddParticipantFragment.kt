package com.trainerview.app.presentation.add_participant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.trainerview.app.R
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentAddParticipantBinding
import com.trainerview.app.di.ScreenComponent
import com.trainerview.app.presentation.add_participant.di.AddParticipantComponent
import com.trainerview.app.presentation.add_participant.di.DaggerAddParticipantComponent
import com.trainerview.app.presentation.update_group.UpdateGroupFragmentArgs
import kotlinx.coroutines.launch

class AddParticipantFragment : BaseFragment<FragmentAddParticipantBinding, AddParticipantViewModel>() {

    companion object {
        const val ADD_PARTICIPANT_REQUEST_KEY = "add_participant_request_key"
        const val ADD_PARTICIPANT_MODEL_KEY = "add_participant_model_key"
    }

    private val args: AddParticipantFragmentArgs by navArgs()

    override val viewModel by injectViewModel<AddParticipantViewModel>()
    override fun diComponent(): AddParticipantComponent = DaggerAddParticipantComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddParticipantBinding {
        return FragmentAddParticipantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.fragmentAddParticipantToolbar) {
            setNavigationOnClickListener { findNavController().popBackStack() }
            applySystemInsetsTop()
        }

        when (val updateMode = args.updateMode) {
            UpdateParticipantType.CreateParticipant -> {
                binding.fragmentAddParticipantSaveButton.setOnClickListener {
                    val participant = CreateParticipantModel(
                        id = null,
                        name = binding.fragmentAddGroupNameInput.text.toString()
                    )
                    setFragmentResult(
                        ADD_PARTICIPANT_REQUEST_KEY,
                        bundleOf(ADD_PARTICIPANT_MODEL_KEY to participant)
                    )
                    findNavController().popBackStack()
                }
            }
            is UpdateParticipantType.EditParticipant -> {
                binding.fragmentAddGroupNameInput.setText(updateMode.participantName, TextView.BufferType.EDITABLE)
                binding.fragmentAddParticipantSaveButton.setOnClickListener {
                    val participant = CreateParticipantModel(
                        id = updateMode.participantId,
                        name = binding.fragmentAddGroupNameInput.text.toString()
                    )
                    setFragmentResult(
                        ADD_PARTICIPANT_REQUEST_KEY,
                        bundleOf(ADD_PARTICIPANT_MODEL_KEY to participant)
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }
}