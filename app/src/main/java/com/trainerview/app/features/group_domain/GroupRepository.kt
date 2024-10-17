package com.trainerview.app.features.group_domain

import com.trainerview.app.features.group_domain.data.model.GroupDb

interface GroupRepository {

    suspend fun getGroups(): List<GroupDb>

    suspend fun getGroup(groupId: Long): GroupDb

    suspend fun insertGroup(group: GroupDb): Long

    suspend fun updateGroup(group: GroupDb)

    suspend fun deleteGroup(groupId: Long)
}
