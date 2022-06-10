package com.unlam.feat.presentation.view.search.event_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.repository.FeatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchEventDetailViewModel
@Inject
constructor(

    private val featRepository: FeatRepository
):ViewModel(){

    private val _state = mutableStateOf(SearchEventDetailState())
    val state: State<SearchEventDetailState> = _state

    fun getDataDetailEvent(idEvent: Int) {
        featRepository.getDataDetailEvent(idEvent).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SearchEventDetailState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SearchEventDetailState(loading = true)
                }
                is Result.Success -> {
                    _state.value = SearchEventDetailState(
                        event = result.data!!.event,
                        playersConfirmed = result.data.playersConfirmed
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}