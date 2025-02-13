package com.trainerview.app.features.update_training_screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

sealed class UpdateTrainingNavParams : Parcelable {

    @Parcelize
    class CreateTraining(
        val participants: List<ParticipantItem>
    ) : UpdateTrainingNavParams()

    @Parcelize
    class UpdateTraining(
        val trainingId: Long,
        val date: Date
    ) : UpdateTrainingNavParams()
}

@Parcelize
class ParticipantItem(
    val id: Long,
    val name: String,
) : Parcelable
