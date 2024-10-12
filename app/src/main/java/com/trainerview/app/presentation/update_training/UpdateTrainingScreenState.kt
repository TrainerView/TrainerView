package com.trainerview.app.presentation.update_training

import java.util.Date

data class UpdateTrainingScreenState(
    val selectedDate: Date,
    val cameParticipants: List<ParticipantItem> = emptyList(),
    val missedParticipants: List<ParticipantItem> = emptyList(),
)