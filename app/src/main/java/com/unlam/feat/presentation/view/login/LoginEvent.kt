package com.unlam.feat.presentation.view.login

sealed class LoginEvent {
    data class EnteredEmailOrPhone(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object Login : LoginEvent()
}
