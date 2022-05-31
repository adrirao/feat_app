package com.unlam.feat.presentation.view.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing : StateFlow<Boolean> = _isRefreshing

    init {
        getEventsCreatedByUser()
    }

    fun getEventsCreatedByUser() {
        featRepository.getEventsCreatedByUser(1).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = LoginState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = LoginState(events = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}
