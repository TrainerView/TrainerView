package com.trainerview.app.features.update_training_screen

import java.util.Date

data class UpdateTrainingScreenState(
    val selectedDate: Date,
    val cameParticipants: List<ParticipantItem> = emptyList(),
    val missedParticipants: List<ParticipantItem> = emptyList(),
)