package com.trainerview.app.presentation.update_participant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UpdateParticipantModel(
    val id: Long?,
    val name: String
) : Parcelable