package com.trainerview.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trainerview.app.data.db.model.GroupDb
import com.trainerview.app.data.db.model.ParticipantDb
import com.trainerview.app.data.db.model.TrainingDb
import com.trainerview.app.data.db.model.TrainingVisitDb

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