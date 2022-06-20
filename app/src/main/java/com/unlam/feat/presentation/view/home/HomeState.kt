package com.unlam.feat.presentation.view.home

import com.unlam.feat.model.Event


data class HomeState(
    val isLoading: Boolean = false,
    val eventOfTheWeek: List<Event> = emptyList(),
    val eventConfirmedOrApplied: List<Event> = emptyList(),
    val error: String = ""

)
