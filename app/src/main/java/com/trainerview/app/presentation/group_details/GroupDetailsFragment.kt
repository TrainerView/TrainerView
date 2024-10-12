package com.trainerview.app.presentation.group_details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.trainerview.app.app.AppComponentHolder
import com.trainerview.app.base.BaseFragment
import com.trainerview.app.databinding.FragmentGroupDetailsBinding
import com.trainerview.app.presentation.group_details.di.DaggerGroupDetailsComponent
import com.trainerview.app.presentation.update_training.ParticipantItem
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        with(binding.fragmentGroupDetailsToolbar) {
            applySystemInsetsTop()
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.fragmentGroupDetailsNewTrainingBtn.setOnClickListener {
            viewModel.showCreateTrainingScreen()
        }

        binding.fragmentGroupDetailsRv.adapter = viewModel.adapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
    }
}


