package com.trainerview.app.features.training_domain

import com.trainerview.app.features.training_domain.data.model.TrainingDb
import com.trainerview.app.features.training_domain.data.model.TrainingVisitDb
import com.trainerview.app.features.training_domain.data.TrainingWithParticipant

interface TrainingRepository {

    suspend fun insertTraining(training: TrainingDb): Long

    suspend fun updateTraining(training: TrainingDb)

    suspend fun insertVisit(trainingVisitDb: TrainingVisitDb): Long

    suspend fun getTrainings(groupId: Long): List<TrainingDb>

    suspend fun deleteAllVisits(trainingId: Long)

    suspend fun getTrainingVisits(trainingId: Long): List<TrainingWithParticipant>
}
