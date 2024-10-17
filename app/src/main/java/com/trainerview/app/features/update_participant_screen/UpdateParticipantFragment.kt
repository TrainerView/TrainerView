package com.trainerview.app.features.update_participant_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.ui.BaseFragment
import com.trainerview.app.databinding.FragmentUpdateParticipantBinding
import com.trainerview.app.features.update_participant_screen.di.UpdateParticipantComponent
import com.trainerview.app.features.update_participant_screen.di.DaggerUpdateParticipantComponent

class UpdateParticipantFragment : BaseFragment<FragmentUpdateParticipantBinding, UpdateParticipantViewModel>() {

    companion object {
        const val ADD_PARTICIPANT_REQUEST_KEY = "add_participant_request_key"
        const val ADD_PARTICIPANT_MODEL_KEY = "add_participant_model_key"
    }

    private val args: UpdateParticipantFragmentArgs by navArgs()

    override val viewModel by injectViewModel<UpdateParticipantViewModel>()
    override fun diComponent(): UpdateParticipantComponent = DaggerUpdateParticipantComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateParticipantBinding {
        return FragmentUpdateParticipantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            toolbar.applySystemInsetsTop()
            saveButton.setOnClickListener {
                viewModel.onSaveClick(nameInput.text.toString())
            }

            when (val params = args.updateParams) {
                UpdateParticipantNavParams.CreateParticipant -> Unit
                is UpdateParticipantNavParams.UpdateParticipant -> {
                    binding.nameInput.setText(params.participantName, TextView.BufferType.EDITABLE)
                }
            }
        }
    }
}
