package com.trainerview.app.features.training_domain

import com.trainerview.app.base.db.RoomDB
import com.trainerview.app.features.training_domain.data.model.TrainingDb
import com.trainerview.app.features.training_domain.data.model.TrainingVisitDb
import com.trainerview.app.features.training_domain.data.TrainingDao
import com.trainerview.app.features.training_domain.data.TrainingWithParticipant
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val roomDb: RoomDB,
): TrainingRepository {

    private val trainingDao: TrainingDao
        get() = roomDb.trainingDao()

    override suspend fun insertTraining(training: TrainingDb): Long {
        return trainingDao.insertTraining(training)
    }

    override suspend fun updateTraining(training: TrainingDb) {
        return trainingDao.updateTraining(training)
    }

    override suspend fun insertVisit(trainingVisit: TrainingVisitDb): Long {
        return trainingDao.insertVisit(trainingVisit)
    }

    override suspend fun getTrainings(groupId: Long): List<TrainingDb> {
        return trainingDao.getTrainings(groupId)
    }

    override suspend fun deleteAllVisits(trainingId: Long) {
        trainingDao.deleteAllVisits(trainingId)
    }

    override suspend fun getTrainingVisits(trainingId: Long): List<TrainingWithParticipant> {
        return trainingDao.getTrainingVisits(trainingId)
    }
}