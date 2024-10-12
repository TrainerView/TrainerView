package com.trainerview.app.presentation.update_training

import androidx.lifecycle.viewModelScope
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.domain.TrainingRepository
import com.trainerview.app.presentation.group_list.GroupListScreenState
import com.trainerview.app.presentation.update_group.ParticipantAdapter
import com.trainerview.app.presentation.update_group.ParticipantListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class UpdateTrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository
) : BaseViewModel() {

    private val args by navArgs<UpdateTrainingFragmentArgs>()
    private val _uiState = MutableStateFlow(UpdateTrainingScreenState(Calendar.getInstance().time))
    val uiState: StateFlow<UpdateTrainingScreenState> = _uiState


    val cameParticipantsAdapter = ParticipantAdapter()
    val missedParticipantsAdapter = ParticipantAdapter()

    init {
        cameParticipantsAdapter.onItemClickListener = { clickedParticipant ->
            val cameParticipants = _uiState.value.cameParticipants.filter {
                clickedParticipant.id != it.id
            }
            val missedParticipants = _uiState.value.missedParticipants.toMutableList().apply {
                val newItem = ParticipantItem(
                    id = clickedParticipant.id,
                    name = clickedParticipant.name
                )
                add(0, newItem)
            }
            _uiState.value = _uiState.value.copy(
                cameParticipants = cameParticipants,
                missedParticipants = missedParticipants
            )
            updateUi()
        }
        missedParticipantsAdapter.onItemClickListener = { clickedParticipant ->
            val cameParticipants = _uiState.value.cameParticipants.toMutableList().apply {
                val newItem = ParticipantItem(
                    id = clickedParticipant.id,
                    name = clickedParticipant.name
                )
                add(0, newItem)
            }
            val missedParticipants = _uiState.value.missedParticipants.filter {
                clickedParticipant.id != it.id
            }
            _uiState.value = _uiState.value.copy(
                cameParticipants = cameParticipants,
                missedParticipants = missedParticipants
            )
            updateUi()
        }
    }

    fun loadData() {
        when (val params = args.updateParams) {
            is UpdateTrainingNavParams.CreateTraining -> {
                _uiState.value = _uiState.value.copy(
                    cameParticipants = emptyList(),
                    missedParticipants = params.participants
                )
                updateUi()
            }
            is UpdateTrainingNavParams.UpdateTraining -> {
                safeLaunch {
                    val visits = withContext(Dispatchers.IO) { trainingRepository.getTrainingVisits(params.trainingId) }

                    val missedParticipants = mutableListOf<ParticipantItem>()
                    val visitedParticipants = mutableListOf<ParticipantItem>()
                    visits.forEach {
                        val participantItem = ParticipantItem(
                            id = it.participant.id,
                            name = it.participant.name
                        )
                        when (it.isVisited) {
                            true -> visitedParticipants.add(participantItem)
                            false -> missedParticipants.add(participantItem)
                        }
                    }
                    _uiState.value = _uiState.value.copy(
                        cameParticipants = visitedParticipants,
                        missedParticipants = missedParticipants,
                        selectedDate = params.date
                    )
                    updateUi()
                }
            }
        }
    }

    private fun updateUi() {
        cameParticipantsAdapter.update(
            _uiState.value.cameParticipants.map {
                ParticipantListItem(
                    id = it.id,
                    name = it.name
                )
            }
        )
        missedParticipantsAdapter.update(
            _uiState.value.missedParticipants.map {
                ParticipantListItem(
                    id = it.id,
                    name = it.name
                )
            }
        )
    }

    fun onDateSelected(date: Date) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }
}