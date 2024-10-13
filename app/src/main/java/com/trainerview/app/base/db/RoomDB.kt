package com.trainerview.app.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trainerview.app.features.group_domain.data.model.GroupDb
import com.trainerview.app.features.participant_domain.data.model.ParticipantDb
import com.trainerview.app.features.training_domain.data.model.TrainingDb
import com.trainerview.app.features.training_domain.data.model.TrainingVisitDb
import com.trainerview.app.features.group_domain.data.GroupDao
import com.trainerview.app.features.participant_domain.data.ParticipantDao
import com.trainerview.app.features.training_domain.data.TrainingDao

@Database(
    entities = [
        GroupDb::class,
        ParticipantDb::class,
        TrainingDb::class,
        TrainingVisitDb::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun participantDao(): ParticipantDao
    abstract fun trainingDao(): TrainingDao
}