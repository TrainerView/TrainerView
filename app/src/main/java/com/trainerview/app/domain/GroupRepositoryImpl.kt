package com.trainerview.app.domain

import com.trainerview.app.data.db.GroupDao
import com.trainerview.app.data.db.RoomDB
import com.trainerview.app.data.db.model.GroupDb
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB
) : GroupRepository{

    private val groupDao: GroupDao
        get() = roomDB.groupDao()

    override suspend fun getGroups() : List<GroupDb> {
        return groupDao.getAllGroups()
    }

    override suspend fun insertGroup(group: GroupDb) {
        groupDao.insertGroup(group)
    }

    override suspend fun deleteGroup(groupId: Long) {
        groupDao.deleteGroup(groupId)
    }
}