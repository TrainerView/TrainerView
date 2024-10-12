package com.trainerview.app.domain

import com.trainerview.app.data.db.TrainingWithParticipant
import com.trainerview.app.data.db.model.TrainingDb
import com.trainerview.app.data.db.model.TrainingVisitDb

interface TrainingRepository {

    suspend fun insertTraining(training: TrainingDb): Long

    suspend fun updateTraining(training: TrainingDb)

    suspend fun insertVisit(trainingVisitDb: TrainingVisitDb): Long

    suspend fun getTrainings(groupId: Long): List<TrainingDb>

    suspend fun deleteAllVisits(trainingId: Long)

    suspend fun getTrainingVisits(trainingId: Long): List<TrainingWithParticipant>
}