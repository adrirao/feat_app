package com.unlam.feat.presentation.view.events.add_event

import com.unlam.feat.model.Event

data class AddEventState(
    val isCreatedMessage: String? = "",

    val error: String = "",
    val isLoading: Boolean = false
)