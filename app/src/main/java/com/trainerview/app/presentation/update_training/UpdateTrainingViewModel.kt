package com.trainerview.app.presentation.update_training

import androidx.core.os.bundleOf
import com.trainerview.app.base.BackWithResult
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.domain.TrainingRepository
import com.trainerview.app.presentation.update_group.ParticipantAdapter
import com.trainerview.app.presentation.update_group.ParticipantListItem
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment.Companion.CREATE_TRAINING_REQUEST_KEY
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment.Companion.DATE_MODEL_KEY
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment.Companion.MISSED_PARTICIPANTS_MODEL_KEY
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment.Companion.TRAINING_ID_KEY
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment.Companion.UPDATE_TRAINING_REQUEST_KEY
import com.trainerview.app.presentation.update_training.UpdateTrainingFragment.Companion.VISITED_PARTICIPANTS_MODEL_KEY
import com.trainerview.app.presentation.update_training.UpdateTrainingNavParams.UpdateTraining
import com.trainerview.app.presentation.update_training.UpdateTrainingNavParams.CreateTraining

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
    private val cameParticipants: List<ParticipantItem>
        get() = _uiState.value.cameParticipants
    private val missedParticipants: List<ParticipantItem>
        get() = _uiState.value.missedParticipants
    private val selectedDate: Date
        get() = _uiState.value.selectedDate

    val uiState: StateFlow<UpdateTrainingScreenState> = _uiState


    val cameParticipantsAdapter = ParticipantAdapter()
    val missedParticipantsAdapter = ParticipantAdapter()

    init {
        cameParticipantsAdapter.onItemClickListener = { clickedParticipant ->
            moveParticipantFromCameToMissed(clickedParticipant.id)
            updateAdapters()
        }
        missedParticipantsAdapter.onItemClickListener = { clickedParticipant ->
            moveParticipantFromMissedToCame(clickedParticipant.id)
            updateAdapters()
        }
    }

    fun loadData() {
        when (val params = args.updateParams) {
            is CreateTraining -> loadDataForCreate(params)
            is UpdateTraining -> loadDataForUpdate(params)
        }
    }

    fun onDateSelected(date: Date) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun onSaveButtonClick() {
        val navEvent = when (val params = args.updateParams) {
            is CreateTraining -> BackWithResult(
                requestKey = CREATE_TRAINING_REQUEST_KEY,
                result = bundleOf(
                    VISITED_PARTICIPANTS_MODEL_KEY to cameParticipants,
                    MISSED_PARTICIPANTS_MODEL_KEY to missedParticipants,
                    DATE_MODEL_KEY to selectedDate
                )
            )
            is UpdateTraining -> BackWithResult(
                requestKey = UPDATE_TRAINING_REQUEST_KEY,
                result = bundleOf(
                    VISITED_PARTICIPANTS_MODEL_KEY to cameParticipants,
                    MISSED_PARTICIPANTS_MODEL_KEY to missedParticipants,
                    DATE_MODEL_KEY to selectedDate,
                    TRAINING_ID_KEY to params.trainingId
                )
            )
        }
        postNavEvents(navEvent)
    }

    private fun loadDataForCreate(params: CreateTraining) {
        _uiState.value = _uiState.value.copy(
            cameParticipants = emptyList(),
            missedParticipants = params.participants
        )
        updateAdapters()
    }

    private fun loadDataForUpdate(params: UpdateTraining) {
        safeLaunch {
            val visits = withContext(Dispatchers.IO) {
                trainingRepository.getTrainingVisits(params.trainingId)
            }

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
            updateAdapters()
        }
    }

    private fun updateAdapters() {
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

    private fun moveParticipantFromCameToMissed(participantId: Long) {
        val participant = cameParticipants.firstOrNull { it.id == participantId } ?: return

        val cameParticipants = cameParticipants.filter {
            participant.id != it.id
        }
        val missedParticipants = missedParticipants.toMutableList().apply {
            val newItem = ParticipantItem(
                id = participantId,
                name = participant.name
            )
            add(0, newItem)
        }
        _uiState.value = _uiState.value.copy(
            cameParticipants = cameParticipants,
            missedParticipants = missedParticipants
        )
    }

    private fun moveParticipantFromMissedToCame(participantId: Long) {
        val participant = missedParticipants.firstOrNull { it.id == participantId } ?: return

        val missedParticipants = missedParticipants.filter {
            participant.id != it.id
        }
        val cameParticipants = cameParticipants.toMutableList().apply {
            val newItem = ParticipantItem(
                id = participantId,
                name = participant.name
            )
            add(0, newItem)
        }
        _uiState.value = _uiState.value.copy(
            cameParticipants = cameParticipants,
            missedParticipants = missedParticipants
        )
    }
}