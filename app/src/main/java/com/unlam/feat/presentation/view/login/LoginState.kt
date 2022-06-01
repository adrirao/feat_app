package com.unlam.feat.presentation.view.login

data class LoginState(
    val emailOrPhoneText: String = "",
    val passwordText: String = "",
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val isPasswordVisible : Boolean = false,
    val isAuthenticate : Boolean = false,
    val authenticateError : AuthenticateError? = null
){
    sealed class EmailError {
        object FieldEmpty : EmailError()
        object InvalidEmail : EmailError()
    }

    sealed class PasswordError {
        object FieldEmpty : PasswordError()
        object InvalidPassword : PasswordError()
        object InputTooShort : PasswordError()
    }
    sealed class AuthenticateError {
        object VerifyEmail : AuthenticateError()
        object UserNotExist : AuthenticateError()
        object InvalidCredentials : AuthenticateError()
    }
}
