package com.trainerview.app.presentation.update_group

import androidx.lifecycle.viewModelScope
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.data.db.model.GroupDb
import com.trainerview.app.data.db.model.ParticipantDb
import com.trainerview.app.domain.GroupRepository
import com.trainerview.app.domain.ParticipantRepository
import com.trainerview.app.presentation.add_participant.CreateParticipantModel
import com.trainerview.app.presentation.group_list.GroupListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class UpdateGroupViewModel @Inject constructor(
    private val repository: GroupRepository,
    private val participantRepository: ParticipantRepository
) : BaseViewModel() {

    private val args by navArgs<UpdateGroupFragmentArgs>()

    private val _uiState = MutableStateFlow(AddGroupScreenState())
    val uiState: StateFlow<AddGroupScreenState> = _uiState

    val adapter = ParticipantAdapter()

    private val clickListener = { selectedParticipant : ParticipantListItem ->
        _uiState.value = _uiState.value.copy(
            participants = _uiState.value.participants.map {
                val isSelected = it.name == selectedParticipant.name
                ParticipantListItem(id = it.id, name = it.name, isSelected = isSelected)
            },
            selectedParticipantId = selectedParticipant.id
        )
        adapter.update(_uiState.value.participants)
    }

    init {
        adapter.onItemLongClickListener = clickListener
    }

    fun createGroup(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = GroupDb(name = name)
            val groupId = repository.insertGroup(group)

            _uiState.value.participants.forEach {
                val participantDb = ParticipantDb(
                    groupId = groupId,
                    name = it.name
                )
                participantRepository.insertParticipant(participantDb)
            }
        }
    }

    fun updateGroup(groupId: Long, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = GroupDb(id = groupId, name = name)
            repository.updateGroup(group)

            participantRepository.deleteParticipantByGroup(groupId)
            _uiState.value.participants.forEach {
                val participantDb = ParticipantDb(
                    groupId = groupId,
                    name = it.name
                )
                participantRepository.insertParticipant(participantDb)
            }
        }
    }

    fun load(groupId: Long) {
        safeLaunch {
            val participantsDb = withContext(Dispatchers.IO) {
                participantRepository.getParticipants(groupId.toLong())
            }
            _uiState.value = _uiState.value.copy(
                participants = participantsDb.map { ParticipantListItem(id = it.id, name = it.name) }
            )
            adapter.update(_uiState.value.participants)
        }
    }

    fun addParticipant(participantModel: CreateParticipantModel) {
        val participantsCopy = when (participantModel.id) {
            null -> uiState.value.participants.toMutableList().apply {
                add(
                    ParticipantListItem(
                        id = UUID.randomUUID().hashCode().toLong(),
                        name = participantModel.name
                    )
                )
            }
            else -> uiState.value.participants.map {
                if (it.id == participantModel.id) {
                    ParticipantListItem(
                        id = participantModel.id,
                        name = participantModel.name
                    )
                } else {
                    it
                }
            }
        }
        _uiState.value = _uiState.value.copy(participants = participantsCopy)
        adapter.update(_uiState.value.participants)
    }

    fun onDeleteButtonClick() {
        safeLaunch {
            _uiState.value.selectedParticipantId?.let { selectedParticipantId ->
                val participantsCopy = uiState.value.participants.filter {
                    it.id != selectedParticipantId
                }
                _uiState.value = _uiState.value.copy(participants = participantsCopy)
                adapter.update(_uiState.value.participants)
            }
        }
        clearParticipantSelection()
    }

    fun clearParticipantSelection() {
        _uiState.value = _uiState.value.copy(
            selectedParticipantId = null,
            participants = _uiState.value.participants.map {
                ParticipantListItem(id = it.id, name = it.name, isSelected = false)
            }
        )
        adapter.update(_uiState.value.participants)
    }
}