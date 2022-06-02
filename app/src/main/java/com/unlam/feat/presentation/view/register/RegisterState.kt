package com.unlam.feat.presentation.view.register

data class RegisterState(
    val featRegister : Boolean = false,
    val usernameText: String = "",
    val usernameError: UsernameError? = null,
    val emailText: String = "rao.adrii@gmail.com",
    val confirmationEmailText:String = "rao.adrii@gmail.com",
    val emailError: EmailError? = null,
    val passwordText: String = "Asd123",
    val confirmationPasswordText: String = "Asd123",
    val passwordError: PasswordError? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isSuccessRegistration: Boolean = false,
    val registrationMessage: RegisterMessage? = null,
    val error: String = ""
) {
    sealed class UsernameError {
        object FieldEmpty : UsernameError()
        object InputTooShort : UsernameError()
    }

    sealed class EmailError {
        object FieldEmpty : EmailError()
        object InvalidEmail : EmailError()
    }

    sealed class PasswordError {
        object FieldEmpty : PasswordError()
        object InvalidPassword : PasswordError()
        object InputTooShort : PasswordError()
    }

    sealed class RegisterMessage {
        object AlreadyExistUser : RegisterMessage()
        object UserCreated : RegisterMessage()
    }
}
