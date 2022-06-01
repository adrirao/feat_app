package com.unlam.feat.presentation.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = MutableSharedFlow<SplashState>()
    val state: SharedFlow<SplashState> = _state.asSharedFlow()

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Authenticate -> {
                authenticateUser()
            }
        }
    }

    private fun authenticateUser() {
        val isAuthenticate: Boolean = firebaseAuthRepository.isLogged()
        viewModelScope.launch {
            _state.emit(
                SplashState(isAuthenticate = isAuthenticate)
            )
        }
    }
}