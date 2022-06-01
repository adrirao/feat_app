package com.unlam.feat.presentation.view.search

import com.unlam.feat.model.Event

data class SearchState(
    val error: String = "",
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false
)