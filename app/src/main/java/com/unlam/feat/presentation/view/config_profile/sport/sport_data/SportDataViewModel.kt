package com.unlam.feat.presentation.view.config_profile.sport.sport_data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
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
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(SportDataState())
    val state: State<SportDataState> = _state

    init {
//        getPerson()
//        getPosition()
//        getLevel()
//        getValuation()
    }

    fun onEvent(event: SportDataEvent) {
        when (event) {
//            is ConfigSportEvent.EnteredLastName -> {
//                _state.value = _state.value.copy(
//                    lastName = event.value
//                )
//            }
        }
    }

    private fun getPerson() {

        val uId = firebaseAuthRepository.getUserId()
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SportDataState(
                            error = result.message
                                ?: resourcesProvider.getString(R.string.error_unknown)
                        )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoadingPerson = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        person = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPosition() {
        featRepository.getPositions().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SportDataState(
                            error = result.message
                                ?: resourcesProvider.getString(R.string.error_unknown)
                        )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoadingPosition = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        positionList = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLevel() {
        featRepository.getLevels().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SportDataState(
                            error = result.message
                                ?: resourcesProvider.getString(R.string.error_unknown)
                        )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoadingLevel = true
                    )
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        levelList = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

//    private fun getValuation() {
//        featRepository.getLevels().onEach { result ->
//            when (result) {
//                is Result.Error -> {
//                    _state.value =
//                        SportDataState(
//                            error = result.message
//                                ?: resourcesProvider.getString(R.string.error_unknown)
//                        )
//                }
//                is Result.Loading -> {
//                    _state.value = _state.value.copy(
//                        isLoadingValuation = true
//                    )
//                }
//                is Result.Success -> {
//                    _state.value = _state.value.copy(
//                        valuationList = result.data
//                    )
//                }
//            }
//
//        }.launchIn(viewModelScope)
//    }


}