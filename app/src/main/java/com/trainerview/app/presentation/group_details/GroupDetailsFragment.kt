package com.trainerview.app.presentation.group_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentGroupDetailsBinding
import com.trainerview.app.presentation.group_details.di.DaggerGroupDetailsComponent
import com.trainerview.app.presentation.update_training.ParticipantItem
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment
import java.util.Date

class GroupDetailsFragment : BaseFragment<FragmentGroupDetailsBinding, GroupDetailsViewModel>() {

    override val viewModel by injectViewModel<GroupDetailsViewModel>()
    override fun diComponent() = DaggerGroupDetailsComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGroupDetailsBinding {
        return FragmentGroupDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeFragmentResult()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
    }

    private fun subscribeFragmentResult() {
        setFragmentResultListener(UpdateTrainingFragment.CREATE_TRAINING_REQUEST_KEY) { key, bundle ->
            val cameParticipants =  bundle.getParcelableArrayList<ParticipantItem>(UpdateTrainingFragment.VISITED_PARTICIPANTS_MODEL_KEY)
            val missedParticipants =  bundle.getParcelableArrayList<ParticipantItem>(UpdateTrainingFragment.MISSED_PARTICIPANTS_MODEL_KEY)
            val date = bundle.getSerializable(UpdateTrainingFragment.DATE_MODEL_KEY, Date::class.java)!!
            viewModel.createTraining(
                date = date,
                visitedParticipants = cameParticipants?.toList() ?: emptyList(),
                missedParticipants = missedParticipants?.toList() ?: emptyList()
            )
        }

        setFragmentResultListener(UpdateTrainingFragment.UPDATE_TRAINING_REQUEST_KEY) { key, bundle ->
            val cameParticipants =  bundle.getParcelableArrayList<ParticipantItem>(UpdateTrainingFragment.VISITED_PARTICIPANTS_MODEL_KEY)
            val missedParticipants =  bundle.getParcelableArrayList<ParticipantItem>(UpdateTrainingFragment.MISSED_PARTICIPANTS_MODEL_KEY)
            val trainingId = bundle.getLong(UpdateTrainingFragment.TRAINING_ID_KEY)
            val date = bundle.getSerializable(UpdateTrainingFragment.DATE_MODEL_KEY, Date::class.java)!!
            viewModel.updateTrainingVisits(
                trainingId = trainingId,
                date = date,
                visitedParticipants = cameParticipants?.toList() ?: emptyList(),
                missedParticipants = missedParticipants?.toList() ?: emptyList()
            )
        }
    }

    private fun initUi() {
        with(binding) {
            toolbar.applySystemInsetsTop()
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            detailsRv.adapter = viewModel.adapter
            createTrainingBtn.setOnClickListener { viewModel.onCreateTrainingClick() }
        }
    }
}


