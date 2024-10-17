package com.trainerview.app.features.update_participant_screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UpdateParticipantModel(
    val id: Long?,
    val name: String
) : Parcelable
