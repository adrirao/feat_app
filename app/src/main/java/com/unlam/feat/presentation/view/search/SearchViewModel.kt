package com.unlam.feat.presentation.view.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.presentation.view.events.EventState
import com.unlam.feat.repository.FeatRepositoryImp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchViewModel
@Inject
constructor(
    private val featRpository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    init {
        getEventsToday()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
        }
    }

    fun getEventsToday() {
        featRpository.getEventsToday().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = SearchState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SearchState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = SearchState(events = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}