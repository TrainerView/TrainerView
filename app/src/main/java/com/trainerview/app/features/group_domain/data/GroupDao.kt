package com.trainerview.app.features.group_domain.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.trainerview.app.features.group_domain.data.model.GroupDb

@Dao
interface GroupDao {

    @Query("SELECT * FROM group_table")
    suspend fun getAllGroups(): List<GroupDb>

    @Query("SELECT * FROM group_table WHERE group_id = :groupId")
    suspend fun getGroup(groupId: Long): GroupDb

    @Insert
    suspend fun insertGroup(group: GroupDb): Long

    @Update
    suspend fun updateGroup(group: GroupDb)

    @Query("DELETE FROM group_table WHERE group_id = :groupId")
    suspend fun deleteGroup(groupId: Long)
}