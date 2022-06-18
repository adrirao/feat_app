package com.unlam.feat.presentation.view.invitation

import com.unlam.feat.model.Event

data class InvitationState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String = ""
)