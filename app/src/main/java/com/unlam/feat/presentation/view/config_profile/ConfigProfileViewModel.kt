package com.unlam.feat.presentation.view.config_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ConfigProfileViewModel: ViewModel() {
    private val _state = mutableStateOf(ConfigProfileState())
    val state: State<ConfigProfileState> = _state

    fun onEvent(event: ConfigProfileEvent){
        when(event){
           is ConfigProfileEvent.EnteredLastName -> {
               _state.value = _state.value.copy(
                   lastName = event.value
               )
            }
            is ConfigProfileEvent.EnteredName -> {
                _state.value = _state.value.copy(
                    name = event.value
                )
            }
            is ConfigProfileEvent.EnteredDateOfBirth ->{
                _state.value = _state.value.copy(
                    dateOfBirth = event.value
                )
            }
            is ConfigProfileEvent.EnteredNickname -> {
                _state.value = _state.value.copy(
                    nickname = event.value
                )
            }
            is ConfigProfileEvent.EnteredSex -> {
                _state.value = _state.value.copy(
                    sex = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressAlias -> {
                _state.value = _state.value.copy(
                    addressAlias = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressStreet -> {
                _state.value = _state.value.copy(
                    addressStreet = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressNumber -> {
                _state.value = _state.value.copy(
                    addressNumber = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressTown -> {
                _state.value = _state.value.copy(
                    addressTown = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressZipCode -> {
                _state.value = _state.value.copy(
                    addressZipCode = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressLatitude -> {
                _state.value = _state.value.copy(
                    addressLatitude = event.value
                )
            }
            is ConfigProfileEvent.EnteredAddressLongitude -> {
                _state.value = _state.value.copy(
                    addressLongitude = event.value
                )
            }
            is ConfigProfileEvent.ShowAlertPermission -> {
                _state.value = _state.value.copy(
                    showAlertPermission = event.value,
                    titleAlert = event.title,
                    descriptionAlert =  event.description
                )
            }
            is ConfigProfileEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    showAlertPermission = false
                )
            }
        }
    }
}