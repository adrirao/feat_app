package com.unlam.feat.presentation.view.config_profile.additional_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel





class ConfigProfileAdditionalInformationViewModel: ViewModel() {

    private val _state = mutableStateOf(ConfigProfileAdditionalInformationState())
    val state: State<ConfigProfileAdditionalInformationState> = _state

    fun onEvent(event: ConfigProfileAdditionalInformationEvent){
        when(event){
            is ConfigProfileAdditionalInformationEvent.EnteredMinAge -> {
                _state.value = _state.value.copy(
                    minAge = event.value
                )
            }
            is ConfigProfileAdditionalInformationEvent.EnteredMaxAge -> {
                _state.value = _state.value.copy(
                    maxAge = event.value
                )
            }
            is ConfigProfileAdditionalInformationEvent.EnteredNotifications -> {
                _state.value = _state.value.copy(
                    notifications = event.value
                )
            }
            is ConfigProfileAdditionalInformationEvent.EnteredWillingDistance -> {
                _state.value = _state.value.copy(
                    willingDistance = event.value
                )
            }
        }
    }
}