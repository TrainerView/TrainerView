package com.trainerview.app.presentation.group_list

import androidx.lifecycle.viewModelScope
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.data.db.model.GroupDb
import com.trainerview.app.domain.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupsListViewModel @Inject constructor(
    private val repository: GroupRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(GroupListScreenState())
    val uiState: StateFlow<GroupListScreenState> = _uiState

    val adapter = GroupAdapter()

    private val clickListener = { selectedGroup : GroupListItem ->
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
        adapter.onItemLongClickListener = clickListener
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

    fun deleteSelectedGroup() {
        viewModelScope.safeLaunch {
            _uiState.value.selectedGroupId?.let {
                val groupsDb = withContext(Dispatchers.IO) {
                    repository.deleteGroup(it)
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

    fun load() {
        viewModelScope.safeLaunch {
            val groupsDb = withContext(Dispatchers.IO) { repository.getGroups() }

            _uiState.value = _uiState.value.copy(
                groups = groupsDb.map { GroupListItem(id = it.id, name = it.name) }
            )
            adapter.update(_uiState.value.groups)
        }
    }
}