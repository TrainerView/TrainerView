package com.trainerview.app.presentation.group_list

data class GroupListScreenState(
    val groups: List<GroupListItem> = emptyList(),
    val selectedGroupId: Long? = null
)