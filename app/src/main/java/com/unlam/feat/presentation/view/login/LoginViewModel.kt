package com.unlam.feat.presentation.view.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.unlam.feat.R
import com.unlam.feat.common.Constants
import com.unlam.feat.common.Result
import com.unlam.feat.model.Person
import com.unlam.feat.presentation.view.events.EventState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmailOrPhone -> {
                _state.value = _state.value.copy(
                    emailOrPhoneText = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = _state.value.copy(
                    passwordText = event.value
                )
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
            is LoginEvent.Login -> {
                validateEmailOrPhone(_state.value.emailOrPhoneText)
                validatePassword(_state.value.passwordText)
                authenticateUser()
                checkIsFirstLogin()

            }
            is LoginEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    emailError = null,
                    passwordError = null,
                    authenticateError = null
                )
            }

        }
    }


    private fun checkIsFirstLogin() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data == null) {
                        _state.value = _state.value.copy(
                            isFirstLogin = true
                        )
                    } else {
                        _state.value = _state.value.copy(
                            isFirstLogin = false
                        )
                    }
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        apiError = LoginState.ApiError.ApiConnectionError
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validatePassword(password: String) {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.FieldEmpty
            )
        }

        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.InputTooShort
            )
            return
        }

        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (!capitalLettersInPassword || !numberInPassword) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.InvalidPassword
            )
            return
        }
        _state.value = _state.value.copy(passwordError = null)
    }

    private fun validateEmailOrPhone(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _state.value = _state.value.copy(
                emailError = LoginState.EmailError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(
            emailError = null
        )
    }

    private fun authenticateUser() {
        var email = if (_state.value.emailError == null) _state.value.emailOrPhoneText else return
        var password = if (_state.value.passwordError == null) _state.value.passwordText else return
        viewModelScope.launch {
            firebaseAuthRepository.authenticate(email, password) { isLoged, error ->
                if (isLoged) {
                    checkIsFirstLogin()
                    _state.value = _state.value.copy(
                        isAuthenticate = true
                    )
                } else {
                    when (error) {
                        is FirebaseAuthInvalidUserException -> {
                            _state.value = _state.value.copy(
                                authenticateError = LoginState.AuthenticateError.UserNotExist
                            )
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            _state.value = _state.value.copy(
                                authenticateError = LoginState.AuthenticateError.InvalidCredentials
                            )
                        }
                        else -> {
                            _state.value = _state.value.copy(
                                authenticateError = LoginState.AuthenticateError.VerifyEmail
                            )
                        }
                    }
                }
            }
        }
    }


}