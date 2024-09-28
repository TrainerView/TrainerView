package com.trainerview.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trainerview.app.data.db.model.GroupDb

@Database(
    entities = [GroupDb::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun groupDao(): GroupDao
}