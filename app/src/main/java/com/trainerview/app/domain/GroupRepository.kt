package com.trainerview.app.domain

import com.trainerview.app.data.db.model.GroupDb

interface GroupRepository {

    suspend fun getGroups() : List<GroupDb>

    suspend fun getGroup(groupId: Long) : GroupDb

    suspend fun insertGroup(group: GroupDb): Long

    suspend fun updateGroup(group: GroupDb)

    suspend fun deleteGroup(groupId: Long)
}