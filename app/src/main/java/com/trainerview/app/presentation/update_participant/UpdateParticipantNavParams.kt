package com.trainerview.app.presentation.update_participant

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