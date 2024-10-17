package com.trainerview.app.features.participant_domain

import com.trainerview.app.features.participant_domain.data.ParticipantDao
import com.trainerview.app.base.db.RoomDB
import com.trainerview.app.features.participant_domain.data.model.ParticipantDb
import javax.inject.Inject

class ParticipantRepositoryImpl @Inject constructor(
    private val roomDB: RoomDB
) : ParticipantRepository {

    private val participantDao: ParticipantDao
        get() = roomDB.participantDao()

    override suspend fun getParticipants(groupId: Long): List<ParticipantDb> {
        return participantDao.getAllParticipants(groupId)
    }

    override suspend fun insertParticipant(participant: ParticipantDb) {
        participantDao.insertParticipant(participant)
    }

    override suspend fun updateParticipant(participant: ParticipantDb) {
        participantDao.updateParticipant(participant)
    }

    override suspend fun deleteParticipant(participantId: Long) {
        participantDao.deleteParticipant(participantId)
    }

    override suspend fun deleteParticipantByGroup(groupId: Long) {
        participantDao.deleteParticipantsByGroup(groupId)
    }
}
