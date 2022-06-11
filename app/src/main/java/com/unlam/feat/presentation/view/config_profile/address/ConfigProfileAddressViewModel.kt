package com.unlam.feat.presentation.view.config_profile.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigProfileAddressViewModel@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
): ViewModel() {

    private val _state = mutableStateOf(ConfigProfileAddressState())
    val state: State<ConfigProfileAddressState> = _state

    fun onEvent(event: ConfigProfileAddressEvent){
        when(event){
            is ConfigProfileAddressEvent.EnteredAddressAlias -> {
                _state.value = _state.value.copy(
                    addressAlias = event.value
                )
            }
            is ConfigProfileAddressEvent.EnteredAddressStreet -> {
                _state.value = _state.value.copy(
                    addressStreet = event.value
                )
            }
            is ConfigProfileAddressEvent.EnteredAddressNumber -> {
                _state.value = _state.value.copy(
                    addressNumber = event.value
                )
            }
            is ConfigProfileAddressEvent.EnteredAddressTown -> {
                _state.value = _state.value.copy(
                    addressTown = event.value
                )
            }
            is ConfigProfileAddressEvent.EnteredAddressZipCode -> {
                _state.value = _state.value.copy(
                    addressZipCode = event.value
                )
            }
            is ConfigProfileAddressEvent.EnteredAddressLatitude -> {
                _state.value = _state.value.copy(
                    addressLatitude = event.value
                )
            }
            is ConfigProfileAddressEvent.EnteredAddressLongitude -> {
                _state.value = _state.value.copy(
                    addressLongitude = event.value
                )
            }
            is ConfigProfileAddressEvent.ShowAlertPermission -> {
                _state.value = _state.value.copy(
                    showAlertPermission = event.value,
                    titleAlert = event.title,
                    descriptionAlert =  event.description
                )
            }
            is ConfigProfileAddressEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    showAlertPermission = false
                )
            }
            is ConfigProfileAddressEvent.SubmitData -> {


            }
        }
    }
}