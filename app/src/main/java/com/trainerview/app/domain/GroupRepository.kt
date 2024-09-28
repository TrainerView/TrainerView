package com.trainerview.app.domain

import com.trainerview.app.data.db.model.GroupDb

interface GroupRepository {

    suspend fun getGroups() : List<GroupDb>

    suspend fun insertGroup(group: GroupDb)

    suspend fun deleteGroup(groupId: Long)
}