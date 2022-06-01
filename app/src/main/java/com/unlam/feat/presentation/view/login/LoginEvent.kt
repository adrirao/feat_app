package com.unlam.feat.presentation.view.login

import com.unlam.feat.presentation.view.register.RegisterEvent

sealed class LoginEvent {
    data class EnteredEmailOrPhone(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object DismissDialog : LoginEvent()
    object Login : LoginEvent()
}
