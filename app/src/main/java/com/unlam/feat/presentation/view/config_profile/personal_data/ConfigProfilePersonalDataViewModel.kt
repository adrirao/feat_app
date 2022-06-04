package com.unlam.feat.presentation.view.config_profile.personal_data



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ConfigProfilePersonalDataViewModel : ViewModel() {

    private val _state = mutableStateOf(ConfigProfilePersonalDataState())
    val state: State<ConfigProfilePersonalDataState> = _state

    fun onEvent(event: ConfigProfilePersonalDataEvent) {
        when (event) {
            is ConfigProfilePersonalDataEvent.EnteredLastName -> {
                _state.value = _state.value.copy(
                    lastName = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredName -> {
                _state.value = _state.value.copy(
                    name = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredDateOfBirth -> {
                _state.value = _state.value.copy(
                    dateOfBirth = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredNickname -> {
                _state.value = _state.value.copy(
                    nickname = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredSex -> {
                _state.value = _state.value.copy(
                    sex = event.value
                )
            }
        }
    }
}