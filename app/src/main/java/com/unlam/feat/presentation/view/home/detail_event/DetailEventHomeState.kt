package com.unlam.feat.presentation.view.home.detail_event

import com.unlam.feat.model.Event

data class DetailEventHomeState(
    val event : Event? = null,
    val error: String = "",
    val loading: Boolean = false,
)