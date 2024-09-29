package com.trainerview.app.presentation.add_participant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CreateParticipantModel(
    val id: Long?,
    val name: String
) : Parcelable