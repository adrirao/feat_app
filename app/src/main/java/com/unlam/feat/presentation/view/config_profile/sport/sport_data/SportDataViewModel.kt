package com.unlam.feat.presentation.view.config_profile.sport.sport_data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.request.RequestPerson
import com.unlam.feat.model.request.RequestPlayer
import com.unlam.feat.presentation.view.config_profile.additional_information.ConfigProfileAdditionalInformationEvent
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataState
import com.unlam.feat.presentation.view.home.detail_event.DetailEventHomeState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SportDataViewModel
@Inject
constructor(
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(SportDataState())
    val state: State<SportDataState> = _state


    fun onEvent(event: SportDataEvent) {
        when (event) {
            is SportDataEvent.SelectedPosition -> {
                _state.value = _state.value.copy(
                    positionId = event.value
                )
            }
            is SportDataEvent.SelectedLevel -> {
                _state.value = _state.value.copy(
                    levelId = event.value
                )
            }
            is SportDataEvent.SelectedValuation -> {
                _state.value = _state.value.copy(
                    valuationId = event.value
                )
            }
            is SportDataEvent.EnteredAbilities -> {
                _state.value = _state.value.copy(
                    abilities = event.value
                )
            }
            is SportDataEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    abilitiesError = false,
                    positionIdError = false,
                    levelIdError = false,
                    valuationIdError = false,
                    error = "",
                    fieldEmpty = ""
                )

            }
            is SportDataEvent.SingOutUser -> {
                firebaseAuthRepository.signOut()
            }
            is SportDataEvent.SubmitData -> {
                when (state.value.sportGenericId) {
                    1, 4 -> {
                        _state.value = _state.value.copy(
                            positionIdError = validateFieldIsNotEmpty(state.value.positionId.toString())
                        )
                        _state.value = _state.value.copy(
                            levelIdError = validateFieldIsNotEmpty(state.value.levelId.toString())
                        )

                    }
                    2, 3 -> {
                        _state.value = _state.value.copy(
                            levelIdError = validateFieldIsNotEmpty(state.value.levelId.toString())
                        )
                    }
                }
                _state.value = _state.value.copy(
                    valuationIdError = validateFieldIsNotEmpty(state.value.valuationId.toString())
                )
                _state.value = _state.value.copy(
                    abilitiesError = validateFieldIsNotEmpty(state.value.abilities)
                )
                validateField()
                createPlayer()

            }
        }
    }


    private fun createPlayer() {


        val abilities = if (!state.value.abilitiesError) _state.value.abilities else return
        val valuation = if (!state.value.valuationIdError) _state.value.valuationId else return
        val person = if(state.value.person != null) _state.value.person?.id else return
        val sport = if(state.value.sportGenericId != null) _state.value.sportGenericId else return
        var position:Int? = null
        var level:Int? = null

        if (state.value.sportGenericId == 1 || state.value.sportGenericId == 4) {
            position = if (!state.value.positionIdError) _state.value.positionId else return
            level = if (!state.value.levelIdError) _state.value.levelId else return
        }
        if (state.value.sportGenericId == 2 || state.value.sportGenericId == 3) {
            level = if (!state.value.levelIdError) _state.value.levelId else return
        }

        val request = RequestPlayer(
            abilities = abilities,
            position = position,
            level = level,
            valuation = valuation,
            person = requireNotNull(person),
            sport = sport
        )

        featRepository.createPlayer(request).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
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


    private fun validateFieldIsNotEmpty(field: String): Boolean {

        val trimmedField = field.trim()
        if (trimmedField.isBlank() || trimmedField == "null") {
            return true

        }
        return false
    }

    private fun validateField() {

        var fieldError: String = ""

        if (state.value.abilitiesError) fieldError += "Aptitudes, "
        if (state.value.positionIdError) fieldError += "Posición, "
        if (state.value.levelIdError) fieldError += "Nivel, "
        if (state.value.valuationIdError) fieldError += "Grado de interés,"

        if (fieldError != "") {
            _state.value = _state.value.copy(
                fieldEmpty = fieldError
            )
        }
    }

    fun getDataSportScreen(sportGenericId: Int) {

        val uId = firebaseAuthRepository.getUserId()
        featRepository.getDataSportScreen(uId, sportGenericId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SportDataState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SportDataState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = SportDataState(
                        levelList = result.data!!.levelList,
                        positionList = result.data.positionList,
                        valuationList = result.data.valuationList,
                        person = result.data.person,
                        sportGenericId = sportGenericId
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


}