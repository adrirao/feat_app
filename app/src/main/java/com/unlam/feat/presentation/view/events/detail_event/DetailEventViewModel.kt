package com.unlam.feat.presentation.view.events.detail_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.presentation.view.home.HomeEvent
import com.unlam.feat.presentation.view.home.detail_event.DetailEventHomeEvent
import com.unlam.feat.presentation.view.home.detail_event.DetailEventHomeState
import com.unlam.feat.repository.FeatRepository
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailEventState())
    val state: State<DetailEventState> = _state



    fun onEvent(event: DetailEventEvent) {
        when (event) {
            is DetailEventEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    loading = false
                )
            }
        }
    }

    fun getDataDetailEvent(idEvent: Int) {
        featRepository.getDataDetailEvent(idEvent).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        DetailEventState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailEventState(
                        event = result.data!!.event,
                        playersApplied = result.data.playersApplied,
                        playersConfirmed = result.data.playersConfirmed,
                        playersSuggested = result.data.playersSuggested
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}