package com.trainerview.app.presentation.add_group

import androidx.lifecycle.viewModelScope
import com.trainerview.app.base.BaseViewModel
import com.trainerview.app.data.db.model.GroupDb
import com.trainerview.app.domain.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddGroupViewModel @Inject constructor(
    private val repository: GroupRepository
) : BaseViewModel() {

    fun createGroup(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = GroupDb(name = name)
            repository.insertGroup(group)
        }
    }
}