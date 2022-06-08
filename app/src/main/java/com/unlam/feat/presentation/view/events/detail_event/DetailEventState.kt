package com.unlam.feat.presentation.view.events.detail_event

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class DetailEventState(
    val loading: Boolean = false,
    val error: String = "",
    val event: Event? = null,
    val playersSuggested: List<Player>? = null,
    val playersConfirmed: List<Player>? = null,
    val playersApplied: List<Player>? = null
)
