package com.trainerview.app.features.training_domain.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.trainerview.app.features.participant_domain.data.model.ParticipantDb
import com.trainerview.app.features.group_domain.data.model.GroupDb

@Entity(
    tableName = "training_table",
    foreignKeys = [
        ForeignKey(
            entity = GroupDb::class,
            parentColumns = arrayOf("group_id"),
            childColumns = arrayOf("group_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrainingDb(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("training_id")
    val id: Long = 0,

    @ColumnInfo(name = "group_id")
    val groupId: Long,

    @ColumnInfo(name = "date")
    val date: String

)

@Entity(
    tableName = "training_visit_table",
    foreignKeys = [
        ForeignKey(
            entity = TrainingDb::class,
            parentColumns = arrayOf("training_id"),
            childColumns = arrayOf("training_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ParticipantDb::class,
            parentColumns = arrayOf("participant_id"),
            childColumns = arrayOf("participant_id"),
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class TrainingVisitDb(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("training_visit_id")
    val id: Long = 0,

    @ColumnInfo(name = "training_id")
    val trainingId: Long,

    @ColumnInfo(name = "participant_id")
    val participantId: Long,

    @ColumnInfo(name = "is_visited")
    val isVisited: Boolean
)
