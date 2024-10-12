package com.trainerview.app.presentation.update_training

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentUpdateTrainingBinding
import com.trainerview.app.date.DateFormatter
import com.trainerview.app.presentation.update_training.di.DaggerUpdateTrainingComponent
import com.trainerview.app.presentation.update_training.di.UpdateTrainingComponent
import java.util.Calendar


class UpdateTrainingFragment : BaseFragment<FragmentUpdateTrainingBinding, UpdateTrainingViewModel>() {

    companion object {
        const val CREATE_TRAINING_REQUEST_KEY = "create_training_request_key"
        const val UPDATE_TRAINING_REQUEST_KEY = "update_training_request_key"
        const val VISITED_PARTICIPANTS_MODEL_KEY = "visited_participants_model_key"
        const val MISSED_PARTICIPANTS_MODEL_KEY = "missed_participants_model_key"
        const val DATE_MODEL_KEY = "date_model_key"
        const val TRAINING_ID_KEY = "training_id_key"
    }

    private val args: UpdateTrainingFragmentArgs by navArgs()


    override val viewModel by injectViewModel<UpdateTrainingViewModel>()

    override fun diComponent(): UpdateTrainingComponent = DaggerUpdateTrainingComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateTrainingBinding {
        return FragmentUpdateTrainingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.fragmentUpdateTrainingToolbar) {
            applySystemInsetsTop()
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
        binding.fragmentUpdateTrainingMissedRv.adapter = viewModel.missedParticipantsAdapter
        binding.fragmentUpdateTrainingCameRv.adapter = viewModel.cameParticipantsAdapter

        binding.fragmentUpdateTrainingSaveButton.setOnClickListener {
            val requestKey = when (args.updateParams) {
                is UpdateTrainingNavParams.CreateTraining -> CREATE_TRAINING_REQUEST_KEY
                is UpdateTrainingNavParams.UpdateTraining -> UPDATE_TRAINING_REQUEST_KEY
            }
            setFragmentResult(
                requestKey = requestKey,
                result = bundleOf(
                    VISITED_PARTICIPANTS_MODEL_KEY to viewModel.uiState.value.cameParticipants,
                    MISSED_PARTICIPANTS_MODEL_KEY to viewModel.uiState.value.missedParticipants,
                    DATE_MODEL_KEY to viewModel.uiState.value.selectedDate,
                    TRAINING_ID_KEY to (args.updateParams as? UpdateTrainingNavParams.UpdateTraining)?.trainingId
                )
            )
            findNavController().popBackStack()
        }
        binding.fragmentUpdateTrainingDatePickBtn.setOnClickListener {
            showDatePicker()
        }

        binding.fragmentUpdateTrainingDatePickLabel.text = DateFormatter.formatString(
            (args.updateParams as? UpdateTrainingNavParams.UpdateTraining)?.date ?: viewModel.uiState.value.selectedDate

        )

        viewModel.loadData()
    }

    fun showDatePicker() {

        DatePickerDialog(requireContext()).apply {
            val previousDate = Calendar.getInstance().apply {
                time = viewModel.uiState.value.selectedDate
            }
            updateDate(
                previousDate.get(Calendar.YEAR),
                previousDate.get(Calendar.MONTH),
                previousDate.get(Calendar.DAY_OF_MONTH)
            )
            setOnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }.time

                viewModel.onDateSelected(selectedDate)
                binding.fragmentUpdateTrainingDatePickLabel.text = DateFormatter.formatString(selectedDate)
            }
            show()
        }
    }
}