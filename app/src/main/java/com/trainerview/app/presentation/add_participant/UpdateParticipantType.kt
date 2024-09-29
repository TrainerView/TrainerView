package com.trainerview.app.presentation.add_participant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class UpdateParticipantType : Parcelable {

    @Parcelize
    data object CreateParticipant : UpdateParticipantType()

    @Parcelize
    class EditParticipant(
        val participantId: Long,
        val participantName: String
    ) : UpdateParticipantType()
}