package com.trainerview.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.trainerview.app.data.db.model.ParticipantDb

@Dao
interface ParticipantDao {

    @Query("SELECT * FROM participant_table WHERE group_id = :groupId")
    suspend fun getAllParticipants(groupId: Long): List<ParticipantDb>

    @Insert
    suspend fun insertParticipant(participant: ParticipantDb)

    @Update
    suspend fun updateParticipant(participant: ParticipantDb)

    @Query("DELETE FROM participant_table WHERE participant_id = :participantId")
    suspend fun deleteParticipant(participantId: Long)

    @Query("DELETE FROM participant_table WHERE group_id = :groupId")
    suspend fun deleteParticipantsByGroup(groupId: Long)
}