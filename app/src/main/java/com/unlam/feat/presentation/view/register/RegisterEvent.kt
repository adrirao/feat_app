package com.unlam.feat.presentation.view.register

sealed class RegisterEvent{
    data class EnteredUsername(val value: String): RegisterEvent()
    data class EnteredEmail(val value: String): RegisterEvent()
    data class EnteredConfirmEmail(val value: String): RegisterEvent()
    data class EnteredPassword(val value: String): RegisterEvent()
    data class EnteredConfirmPassword(val value: String): RegisterEvent()
    object TogglePasswordVisibility : RegisterEvent()
    object ToggleConfirmPasswordVisibility : RegisterEvent()
    object DismissDialog : RegisterEvent()
    object Register : RegisterEvent()
}
