package com.trainerview.app.features.group_domain

import com.trainerview.app.features.group_domain.data.GroupDao
import com.trainerview.app.base.db.RoomDB
import com.trainerview.app.features.group_domain.data.model.GroupDb
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB
) : GroupRepository {

    private val groupDao: GroupDao
        get() = roomDB.groupDao()

    override suspend fun getGroups() : List<GroupDb> {
        return groupDao.getAllGroups()
    }

    override suspend fun getGroup(groupId: Long): GroupDb {
        return groupDao.getGroup(groupId)
    }

    override suspend fun insertGroup(group: GroupDb): Long {
        return groupDao.insertGroup(group)
    }

    override suspend fun updateGroup(group: GroupDb) {
        groupDao.updateGroup(group)
    }

    override suspend fun deleteGroup(groupId: Long) {
        groupDao.deleteGroup(groupId)
    }
}