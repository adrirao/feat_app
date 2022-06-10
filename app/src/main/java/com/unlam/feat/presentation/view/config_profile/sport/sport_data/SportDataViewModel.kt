package com.unlam.feat.presentation.view.config_profile.sport.sport_data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
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
    private val resourcesProvider: ResourcesProvider,
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
        }
    }

     fun getDataSportScreen(sportGenericId:Int){

        val uId = firebaseAuthRepository.getUserId()
        featRepository.getDataSportScreen(uId,sportGenericId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        SportDataState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = SportDataState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = SportDataState(levelList = result.data!!.levelList, positionList = result.data.positionList, valuationList = result.data.valuationList, person = result.data.person)
                }
            }
        }.launchIn(viewModelScope)
    }


}