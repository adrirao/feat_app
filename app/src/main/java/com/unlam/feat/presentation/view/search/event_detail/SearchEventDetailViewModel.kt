package com.unlam.feat.presentation.view.search.event_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.request.RequestSearchEventApply
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchEventDetailViewModel
@Inject
constructor(
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp,
    private val firebaseRepository: FirebaseAuthRepositoryImp
):ViewModel(){

    private val _state = mutableStateOf(SearchEventDetailState())
    val state: State<SearchEventDetailState> = _state

    fun onEvent(event:SearchEventDetailEvent){
        when (event){
            is SearchEventDetailEvent.DismissDialog ->{
                _state.value = _state.value.copy(
                    error = "", loading = false
                )
            }
            is SearchEventDetailEvent.ApplyEvent ->{
                applyEvent()
            }
        }
    }

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

    fun applyEvent() {

        val uid=firebaseRepository.getUserId()

        val request = RequestSearchEventApply(
            userUid = uid,
            eventId = _state.value.event!!.id,
            origin  = "P"
        )

        featRepository.postEventApply(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error=result.message!!
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        loading = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        success = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}