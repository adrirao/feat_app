package com.unlam.feat.presentation.view.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    val marker = mutableStateOf(com.unlam.feat.presentation.component.map.Marker())

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
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getEventsToday(uId).onEach { result ->
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