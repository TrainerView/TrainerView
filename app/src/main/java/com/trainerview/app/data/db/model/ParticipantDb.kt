package com.trainerview.app.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "participant_table",
    foreignKeys = [
        ForeignKey(
            entity = GroupDb::class,
            parentColumns = arrayOf("group_id"),
            childColumns = arrayOf("group_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ParticipantDb(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("participant_id")
    val id: Long = 0,

    @ColumnInfo(name = "group_id")
    val groupId: Long,

    val name: String,
)