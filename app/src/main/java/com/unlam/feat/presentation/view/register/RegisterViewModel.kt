package com.unlam.feat.presentation.view.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.unlam.feat.common.Constants
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestUser
import com.unlam.feat.repository.FeatRepository
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredUsername -> {
                _state.value = _state.value.copy(
                    usernameText = event.value
                )
            }
            is RegisterEvent.EnteredConfirmEmail -> {
                _state.value = _state.value.copy(
                    confirmationEmailText = event.value
                )
            }
            is RegisterEvent.EnteredConfirmPassword -> {
                _state.value = _state.value.copy(
                    confirmationPasswordText = event.value
                )
            }
            is RegisterEvent.EnteredEmail -> {
                _state.value = _state.value.copy(
                    emailText = event.value
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _state.value = _state.value.copy(
                    passwordText = event.value
                )
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !state.value.isPasswordVisible
                )
            }
            is RegisterEvent.ToggleConfirmPasswordVisibility -> {
                _state.value = _state.value.copy(
                    isConfirmPasswordVisible = !state.value.isConfirmPasswordVisible
                )
            }
            is RegisterEvent.Register -> {
//                validateUsername(state.value.usernameText)
                validateEmail(state.value.emailText)
                validatePassword(state.value.passwordText)
                registerUser()
            }
            is RegisterEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    registrationMessage = null,
                    passwordError = null,
                    emailError = null
                )
            }
        }
    }

    private fun registerUser() {
        val email = if (_state.value.emailError == null) _state.value.emailText else return
        val password = if (_state.value.passwordError == null) _state.value.passwordText else return

        firebaseAuthRepository.register(email, password) { isSuccessRegistration, error ->
            if (isSuccessRegistration) {
                val uid = firebaseAuthRepository.getUserId()
                val request = RequestUser(uid = uid, email = email, "2")
                featRepository.createUser(request).onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(
                                isSuccessRegistration = true,
                                registrationMessage = RegisterState.RegisterMessage.UserCreated
                            )
                        }
                        is Result.Error -> {
                            Log.e("Rao", result.message.toString())
                            _state.value = _state.value.copy(
                                error = result.message!!
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            } else if (error is FirebaseAuthUserCollisionException) {
                _state.value = _state.value.copy(
                    registrationMessage = RegisterState.RegisterMessage.AlreadyExistUser
                )
            }
        }
    }

    private fun validateUsername(username: String) {
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()) {
            _state.value = _state.value.copy(
                usernameError = RegisterState.UsernameError.FieldEmpty
            )
            return
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            _state.value = _state.value.copy(
                usernameError = RegisterState.UsernameError.InputTooShort
            )
            return
        }
        _state.value = _state.value.copy(usernameError = null)
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _state.value = _state.value.copy(
                emailError = RegisterState.EmailError.FieldEmpty
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(
                emailError = RegisterState.EmailError.InvalidEmail
            )
            return
        }
        _state.value = _state.value.copy(emailError = null)
    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.FieldEmpty
            )
            return
        }
        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.InputTooShort
            )
            return
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                passwordError = RegisterState.PasswordError.InvalidPassword
            )
            return
        }
        _state.value = _state.value.copy(passwordError = null)
    }

}