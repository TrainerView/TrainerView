package com.trainerview.app.features.group_list_screen

import com.trainerview.app.base.ui.BaseViewModel
import com.trainerview.app.base.ui.NavigateTo
import com.trainerview.app.features.group_domain.GroupRepository
import com.trainerview.app.features.update_group_screen.UpdateGroupNavParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupsListViewModel @Inject constructor(
    private val repository: GroupRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(GroupListScreenState())
    private val selectedGroup: GroupListItem?
        get() = _uiState.value.groups.firstOrNull { _uiState.value.selectedGroupId == it.id }

    val uiState: StateFlow<GroupListScreenState> = _uiState

    val adapter = GroupAdapter()

    private val onItemClickListener = { selectedGroup : GroupListItem ->
        postNavEvents(
            NavigateTo(
                GroupsListFragmentDirections.actionToGroupDetailsFragment(selectedGroup.id)
            )
        )
    }
    private val onItemLongClickListener = { selectedGroup : GroupListItem ->
        _uiState.value = _uiState.value.copy(
            groups = _uiState.value.groups.map {
                val isSelected = it.name == selectedGroup.name
                GroupListItem(id = it.id, name = it.name, isSelected = isSelected)
            },
            selectedGroupId = selectedGroup.id
        )
        adapter.update(_uiState.value.groups)
    }

    init {
        adapter.onItemClickListener = onItemClickListener
        adapter.onItemLongClickListener = onItemLongClickListener
    }


    fun clearGroupSelection() {
        _uiState.value = _uiState.value.copy(
            selectedGroupId = null,
            groups = _uiState.value.groups.map {
                GroupListItem(id = it.id, name = it.name, isSelected = false)
            }
        )
        adapter.update(_uiState.value.groups)
    }

    fun onCreateGroupClick() {
        clearGroupSelection()
        postNavEvents(
            NavigateTo(
                GroupsListFragmentDirections.actionToAddGroupFragment(
                    UpdateGroupNavParams.CreateGroup
                )
            )
        )
    }

    fun onDeleteSelectedGroupClick() {
        safeLaunch {
            selectedGroup?.id?.let { selectedGroupId ->
                val groupsDb = withContext(Dispatchers.IO) {
                    repository.deleteGroup(selectedGroupId)
                    repository.getGroups()
                }
                _uiState.value = _uiState.value.copy(
                    groups = groupsDb.map { GroupListItem(id = it.id, name = it.name) },
                    selectedGroupId = null
                )
                adapter.update(_uiState.value.groups)
            }
        }
    }

    fun onEditSelectedGroupClick() {
        selectedGroup?.let { selectedGroup ->
            clearGroupSelection()
            postNavEvents(
                NavigateTo(
                    GroupsListFragmentDirections.actionToAddGroupFragment(
                        UpdateGroupNavParams.EditGroup(
                            groupId = selectedGroup.id,
                            groupName = selectedGroup.name
                        )
                    )
                )
            )
        }
    }

    fun load() {
        safeLaunch {
            val groupsDb = withContext(Dispatchers.IO) { repository.getGroups() }
            _uiState.value = _uiState.value.copy(
                groups = groupsDb.map { GroupListItem(id = it.id, name = it.name) }
            )
            adapter.update(_uiState.value.groups)
        }
    }
}