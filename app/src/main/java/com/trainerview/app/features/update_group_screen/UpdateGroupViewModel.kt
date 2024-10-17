package com.trainerview.app.features.update_group_screen

import androidx.lifecycle.viewModelScope
import com.trainerview.app.base.ui.Back
import com.trainerview.app.base.ui.BaseViewModel
import com.trainerview.app.base.ui.NavigateTo
import com.trainerview.app.features.group_domain.data.model.GroupDb
import com.trainerview.app.features.participant_domain.data.model.ParticipantDb
import com.trainerview.app.features.group_domain.GroupRepository
import com.trainerview.app.features.participant_domain.ParticipantRepository
import com.trainerview.app.features.update_participant_screen.UpdateParticipantModel
import com.trainerview.app.features.update_participant_screen.UpdateParticipantNavParams
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
    private val selectedParticipant: ParticipantListItem?
        get() = with(_uiState.value) {
            participants.firstOrNull { it.id == selectedParticipantId }
        }
    private val isParticipantSelected: Boolean
        get() = selectedParticipant != null

    val uiState: StateFlow<AddGroupScreenState> = _uiState

    val adapter = ParticipantAdapter()

    private val clickListener = { selectedParticipant: ParticipantListItem ->
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

    fun addParticipant(participantModel: UpdateParticipantModel) {
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

    fun onToolbarNavIconClick() {
        when (isParticipantSelected) {
            true -> clearParticipantSelection()
            false -> postNavEvents(Back)
        }
    }

    fun onEditSelectedParticipantClick() {
        selectedParticipant?.let { selectedParticipant ->
            clearParticipantSelection()
            postNavEvents(
                NavigateTo(
                    UpdateGroupFragmentDirections.actionToAddParticipantFragment(
                        UpdateParticipantNavParams.UpdateParticipant(
                            participantId = selectedParticipant.id,
                            participantName = selectedParticipant.name
                        )
                    )
                )
            )
        }
    }

    fun onSaveButtonClick(name: String) {
        when (val params = args.updateParams) {
            UpdateGroupNavParams.CreateGroup -> {
                createGroup(name)
                postNavEvents(Back)
            }
            is UpdateGroupNavParams.EditGroup -> {
                updateGroup(params.groupId, name)
                postNavEvents(Back)
            }
        }
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
