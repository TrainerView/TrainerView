package com.trainerview.app.features.update_training_screen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.ui.BaseFragment
import com.trainerview.app.databinding.FragmentUpdateTrainingBinding
import com.trainerview.app.utils.DateFormatter
import com.trainerview.app.features.update_training_screen.di.DaggerUpdateTrainingComponent
import com.trainerview.app.features.update_training_screen.di.UpdateTrainingComponent
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class UpdateTrainingFragment : BaseFragment<FragmentUpdateTrainingBinding, UpdateTrainingViewModel>() {

    companion object {
        const val CREATE_TRAINING_REQUEST_KEY = "create_training_request_key"
        const val UPDATE_TRAINING_REQUEST_KEY = "update_training_request_key"

        const val VISITED_PARTICIPANTS_MODEL_KEY = "visited_participants_model_key"
        const val MISSED_PARTICIPANTS_MODEL_KEY = "missed_participants_model_key"
        const val DATE_MODEL_KEY = "date_model_key"
        const val TRAINING_ID_KEY = "training_id_key"
    }

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
        initUI()
        viewModel.loadData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    onDateChanged(state.selectedDate)
                }
            }
        }
    }

    private fun initUI() {
        with(binding) {
            toolbar.applySystemInsetsTop()
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            missedRv.adapter = viewModel.missedParticipantsAdapter
            cameRv.adapter = viewModel.cameParticipantsAdapter
            saveButton.setOnClickListener {
                viewModel.onSaveButtonClick()
            }

            datePickBtn.setOnClickListener {
                showDatePicker()
            }
        }
    }

    private fun onDateChanged(date: Date) {
        binding.datePickLabel.text = DateFormatter.formatString(date)
    }

    private fun showDatePicker() {

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
            }
            show()
        }
    }
}
