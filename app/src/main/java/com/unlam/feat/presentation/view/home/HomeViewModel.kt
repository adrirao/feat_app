package com.unlam.feat.presentation.view.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.presentation.view.events.EventState
import com.unlam.feat.presentation.view.home.HomeEvent
import com.unlam.feat.presentation.view.home.data.Event
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getEventsByUser()
    }

    fun getEventsByUser() {
        featRepository.getEventsCreatedByUser(1).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = HomeState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = HomeState(events = (result.data ?: emptyList()))
                }
            }
        }.launchIn(viewModelScope)
    }


}
