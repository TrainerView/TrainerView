package com.trainerview.app.features.update_participant_screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class UpdateParticipantNavParams : Parcelable {

    @Parcelize
    data object CreateParticipant : UpdateParticipantNavParams()

    @Parcelize
    class UpdateParticipant(
        val participantId: Long,
        val participantName: String
    ) : UpdateParticipantNavParams()
}