package com.unlam.feat.presentation.view.home

import com.unlam.feat.model.Event
import com.unlam.feat.model.HomeEvent


data class HomeState(
    val isLoading: Boolean = false,
    val eventOfTheWeek: List<Event> = emptyList(),
    val eventConfirmedOrApplied: List<HomeEvent> = emptyList(),
    val error: String = ""

)
