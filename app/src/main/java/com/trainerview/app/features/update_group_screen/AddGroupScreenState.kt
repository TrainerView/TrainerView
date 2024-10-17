package com.trainerview.app.features.update_group_screen

data class AddGroupScreenState(
    val participants: List<ParticipantListItem> = emptyList(),
    val selectedParticipantId: Long? = null
)
