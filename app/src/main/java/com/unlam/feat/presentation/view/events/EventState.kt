package com.unlam.feat.presentation.view.events

import com.unlam.feat.model.Event

data class EventState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String = ""
)
