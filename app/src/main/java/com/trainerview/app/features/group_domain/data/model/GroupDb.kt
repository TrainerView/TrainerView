package com.trainerview.app.features.group_domain.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_table")
data class GroupDb(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("group_id")
    val id: Long = 0,

    val name: String
)
