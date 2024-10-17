package com.trainerview.app.features.group_list_screen

data class GroupListScreenState(
    val groups: List<GroupListItem> = emptyList(),
    val selectedGroupId: Long? = null
)
