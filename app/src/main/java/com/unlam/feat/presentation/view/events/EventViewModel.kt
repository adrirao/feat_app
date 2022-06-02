package com.unlam.feat.presentation.view.events

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.common.Result
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventViewModel
@Inject
constructor(
    private val resourcesProvider: com.unlam.feat.di.ResourcesProvider,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(EventState())
    val state: State<EventState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getEventsCreatedByUser()
    }

    fun onEvent(event: EventEvent) {
        when (event) {
            is EventEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
        }
    }

    fun getEventsCreatedByUser() {
        featRepository.getEventsCreatedByUser(1).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        EventState(error = result.message ?: resourcesProvider.getString(R.string.error_unknown))
                }
                is Result.Loading -> {
                    _state.value = EventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EventState(events = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}
