package com.trainerview.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.trainerview.app.data.db.model.GroupDb

@Dao
interface GroupDao {

    @Query("SELECT * FROM group_table")
    suspend fun getAllGroups(): List<GroupDb>

    @Insert
    suspend fun insertGroup(group: GroupDb)

    @Query("DELETE FROM group_table WHERE group_id = :groupId")
    suspend fun deleteGroup(groupId: Long)
}