package com.unlam.feat.presentation.view.login

import com.unlam.feat.model.Event

data class LoginState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String = ""
)
