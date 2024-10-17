package com.trainerview.app.features.participant_domain

import com.trainerview.app.features.participant_domain.data.model.ParticipantDb

interface ParticipantRepository {

    suspend fun getParticipants(groupId: Long): List<ParticipantDb>

    suspend fun insertParticipant(participant: ParticipantDb)

    suspend fun updateParticipant(participant: ParticipantDb)

    suspend fun deleteParticipant(participantId: Long)

    suspend fun deleteParticipantByGroup(groupId: Long)
}
