package com.unlam.feat.presentation.view.config_profile.availability

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel





class ConfigProfileAvailabilityViewModel: ViewModel() {

    private val _state = mutableStateOf(ConfigProfileAvailabilityState())
    val state: State<ConfigProfileAvailabilityState> = _state

    fun onEvent(event: ConfigProfileAvailabilityEvent){
        when(event){
            is ConfigProfileAvailabilityEvent.EnteredStartTime1 -> {
                _state.value = _state.value.copy(
                    startTime1 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime1-> {
                _state.value = _state.value.copy(
                    endTime1 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime2 -> {
                _state.value = _state.value.copy(
                    startTime2 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime2-> {
                _state.value = _state.value.copy(
                    endTime2 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime3 -> {
                _state.value = _state.value.copy(
                    startTime3 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime3-> {
                _state.value = _state.value.copy(
                    endTime3 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime4 -> {
                _state.value = _state.value.copy(
                    startTime4 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime4-> {
                _state.value = _state.value.copy(
                    endTime4 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime5 -> {
                _state.value = _state.value.copy(
                    startTime5 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime5-> {
                _state.value = _state.value.copy(
                    endTime5 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime6 -> {
                _state.value = _state.value.copy(
                    startTime6 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime6-> {
                _state.value = _state.value.copy(
                    endTime6 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime7 -> {
                _state.value = _state.value.copy(
                    startTime7 = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime7-> {
                _state.value = _state.value.copy(
                    endTime7 = event.value
                )
            }

        }
    }
}