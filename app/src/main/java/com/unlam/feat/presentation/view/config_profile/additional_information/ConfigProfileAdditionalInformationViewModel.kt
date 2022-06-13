package com.unlam.feat.presentation.view.config_profile.additional_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestUpdatePerson
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConfigProfileAdditionalInformationViewModel @Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(ConfigProfileAdditionalInformationState())
    val state: State<ConfigProfileAdditionalInformationState> = _state

    init {
        val uId = firebaseAuthRepository.getUserId()
        getPerson(uId)
    }


    fun onEvent(event: ConfigProfileAdditionalInformationEvent) {
        when (event) {
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
            is ConfigProfileAdditionalInformationEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    ageError = null,
                    error = null,
                )

            }
            is ConfigProfileAdditionalInformationEvent.SingOutUser -> {
                firebaseAuthRepository.signOut()
            }
            is ConfigProfileAdditionalInformationEvent.SubmitData -> {
                validateAgeIsNotEmptyAndValid(state.value.minAge, state.value.maxAge)
                updatePerson()

            }
        }
    }


    private fun updatePerson() {
        val minAge = if (state.value.ageError == null) _state.value.minAge else return
        val maxAge = if (state.value.ageError == null) _state.value.maxAge else return
        val notifications = if(state.value.notifications) 1 else 0
        val willingDistance = state.value.willingDistance
        val personId = if (state.value.personError  == null) _state.value.personId else return

        val request = RequestUpdatePerson(
            minAge = minAge.toInt(),
            maxAge = maxAge.toInt(),
            notifications = notifications,
            willingDistance = willingDistance.toInt(),
            id = requireNotNull(personId)
        )

            featRepository.updatePerson(request).onEach { result->
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

    private fun getPerson(uId: String) {
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value =
                        ConfigProfileAdditionalInformationState(personId = result.data?.id)

                }
                is Result.Loading -> {
                    _state.value = ConfigProfileAdditionalInformationState(isLoading = true)

                }
                is Result.Error -> {
                    _state.value = ConfigProfileAdditionalInformationState(
                        personError = result.message ?: "Error Inesperado"
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun validateAgeIsNotEmptyAndValid(minAge: String, maxAge: String) {
        val trimmedMinAge = minAge.trim()
        val trimmedMaxAge = maxAge.trim()

        if (trimmedMaxAge.isBlank() && trimmedMinAge.isBlank()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileAdditionalInformationState.RangeAgeError.FieldEmpty
            )
            return
        }
        if (trimmedMinAge.isBlank()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileAdditionalInformationState.RangeAgeError.MinAgeEmpty
            )
            return
        }
        if (trimmedMaxAge.isBlank()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileAdditionalInformationState.RangeAgeError.MaxAgeEmpty
            )
            return
        }

        if (minAge.toInt() > maxAge.toInt()) {
            _state.value = _state.value.copy(
                ageError = ConfigProfileAdditionalInformationState.RangeAgeError.IsInvalidRange
            )
            return
        }

        _state.value = _state.value.copy(ageError = null)

    }
}

