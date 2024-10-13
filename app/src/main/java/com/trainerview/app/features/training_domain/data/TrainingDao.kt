package com.trainerview.app.features.training_domain.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.trainerview.app.features.participant_domain.data.model.ParticipantDb
import com.trainerview.app.features.training_domain.data.model.TrainingDb
import com.trainerview.app.features.training_domain.data.model.TrainingVisitDb

@Dao
interface TrainingDao {

    @Insert
    suspend fun insertTraining(training: TrainingDb): Long

    @Update
    suspend fun updateTraining(training: TrainingDb)

    @Insert
    suspend fun insertVisit(trainingVisitDb: TrainingVisitDb): Long

    @Query("SELECT * FROM training_table WHERE group_id = :groupId")
    suspend fun getTrainings(groupId: Long): List<TrainingDb>

    @Query(
        "SELECT visit.is_visited, participant.participant_id, participant.group_id, participant.name " +
                "FROM training_visit_table as visit, participant_table as participant " +
                "WHERE (visit.participant_id == participant.participant_id AND visit.training_id = :trainingId)"
    )
    suspend fun getTrainingVisits(trainingId: Long): List<TrainingWithParticipant>

    @Query("DELETE FROM training_visit_table WHERE training_id = :trainingId")
    suspend fun deleteAllVisits(trainingId: Long)
}

class TrainingWithParticipant(

    @ColumnInfo(name = "is_visited")
    val isVisited: Boolean,

    @Embedded
    val participant: ParticipantDb,

)