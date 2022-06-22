package com.unlam.feat.presentation.view.config_profile.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestAddress
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConfigProfileAddressViewModel @Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(ConfigProfileAddressState())
    val state: State<ConfigProfileAddressState> = _state

    init {
        val uId = firebaseAuthRepository.getUserId()
        getPerson(uId)
    }


    fun onEvent(event: ConfigProfileAddressEvent) {
        when (event) {
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
                    descriptionAlert = event.description
                )
            }
            is ConfigProfileAddressEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    showAlertPermission = false,
                    addressStreetError = null,
                    addressNumberError = null,
                    addressTownError = null,
                    addressZipCodeError = null,
                    fieldEmpty = ""
                )
            }
            is ConfigProfileAddressEvent.SingOutUser -> {
                firebaseAuthRepository.signOut()
            }
            is ConfigProfileAddressEvent.SubmitData -> {
                validateStreetIsNotNUll(state.value.addressStreet)
                validateNumberIsNotNUll(state.value.addressNumber)
                validateTownIsNotNUll(state.value.addressTown)
                validateZipCodeIsNotNUll(state.value.addressZipCode)
                validateField()
                addAddress()

            }
        }
    }


    private fun getPerson(uId: String) {
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = ConfigProfileAddressState(personId = result.data?.id)
                }
                is Result.Loading -> {
                    _state.value = ConfigProfileAddressState(isLoading = true)
                }
                is Result.Error -> {
                    _state.value = ConfigProfileAddressState(
                        personError = result.message ?: "Error Inesperado"
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    private fun addAddress() {
        val addressAlias = state.value.addressAlias
        val addressStreet =
            if (state.value.addressStreetError == null) _state.value.addressStreet else return
        val addressNumber =
            if (state.value.addressNumberError == null) _state.value.addressNumber else return
        val addressTown =
            if (state.value.addressTownError == null) _state.value.addressTown else return
        val addressZipCode =
            if (state.value.addressZipCodeError == null) _state.value.addressZipCode else return
        val addressLatitude =
            if (state.value.addressLatitude != "") _state.value.addressLatitude else return
        val addressLongitude =
            if (state.value.addressLongitude != "") _state.value.addressLongitude else return
        val personId = if (state.value.personId != null) _state.value.personId else return

        val request = RequestAddress(
            alias = addressAlias,
            street = addressStreet,
            number = addressNumber,
            town = addressTown,
            zipCode = addressZipCode,
            latitude = addressLatitude,
            logitude = addressLongitude,
            person = requireNotNull(personId)
        )

        featRepository.addAddress(request).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun validateField() {

        var fieldError: String = ""

        if (state.value.addressStreetError == ConfigProfileAddressState.AddressStreetError.FieldEmpty) fieldError += "Calle, "
        if (state.value.addressNumberError == ConfigProfileAddressState.AddressNumberError.FieldEmpty) fieldError += "Numero, "
        if (state.value.addressTownError == ConfigProfileAddressState.AddressTownError.FieldEmpty) fieldError += "Ciudad, "
        if (state.value.addressZipCodeError == ConfigProfileAddressState.AddressZipCodeError.FieldEmpty) fieldError += "Codigo postal"

        if (fieldError != "") {
            _state.value = _state.value.copy(
                fieldEmpty = fieldError
            )
        }
    }


    private fun validateStreetIsNotNUll(street: String) {
        val trimmedLastName = street.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressStreetError = ConfigProfileAddressState.AddressStreetError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressStreetError = null)

    }

    private fun validateNumberIsNotNUll(number: String) {
        val trimmedLastName = number.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressNumberError = ConfigProfileAddressState.AddressNumberError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressNumberError = null)

    }

    private fun validateTownIsNotNUll(town: String) {
        val trimmedLastName = town.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressTownError = ConfigProfileAddressState.AddressTownError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressTownError = null)

    }

    private fun validateZipCodeIsNotNUll(zipCode: String) {
        val trimmedLastName = zipCode.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                addressZipCodeError = ConfigProfileAddressState.AddressZipCodeError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(addressZipCodeError = null)

    }
}