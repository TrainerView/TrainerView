package com.trainerview.app.presentation.update_group

data class AddGroupScreenState(
    val participants: List<ParticipantListItem> = emptyList(),
    val selectedParticipantId: Long? = null
)