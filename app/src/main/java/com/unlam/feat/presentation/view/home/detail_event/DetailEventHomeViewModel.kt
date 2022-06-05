package com.unlam.feat.presentation.view.home.detail_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.presentation.view.home.HomeState
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailEventHomeViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp
): ViewModel(){
    private val _state = mutableStateOf(DetailEventHomeState())
    val state : State<DetailEventHomeState> = _state

    fun onEvent(event:DetailEventHomeEvent){
        when(event){
            is DetailEventHomeEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    loading = false
                )
            }
        }
    }

    fun getDetailEvent(idEvent: Int){
        featRepository.getEventById(idEvent).onEach {result ->
            when (result) {
                is Result.Error -> {
                    _state.value = DetailEventHomeState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventHomeState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailEventHomeState(event = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}