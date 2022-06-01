package com.unlam.feat.presentation.view.events.add_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp
): ViewModel() {

    private val _state = mutableStateOf(AddEventState())
    val state: State<AddEventState> = _state


    fun onEvent(event:AddEventEvent){
        when(event){
            is AddEventEvent.EnteredName -> {
                _state.value = _state.value.copy(
//                    event = _state.value.event.name
                )
            }
        }
    }

    fun createEvent() {

        featRepository.postEvent().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = AddEventState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = AddEventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = AddEventState(isCreatedMessage = result.data)
                }
            }
        }.launchIn(viewModelScope)


    }
}