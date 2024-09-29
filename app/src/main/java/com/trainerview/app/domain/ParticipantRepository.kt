package com.trainerview.app.domain

import com.trainerview.app.data.db.model.ParticipantDb

interface ParticipantRepository {

    suspend fun getParticipants(groupId: Long) : List<ParticipantDb>

    suspend fun insertParticipant(participant: ParticipantDb)

    suspend fun updateParticipant(participant: ParticipantDb)

    suspend fun deleteParticipant(participantId: Long)

    suspend fun deleteParticipantByGroup(groupId: Long)
}