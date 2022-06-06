package com.unlam.feat.presentation.view.config_profile.sport

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.presentation.view.events.EventState
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConfigSportViewModel
@Inject
constructor(
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp,
)
: ViewModel() {
    private val _state = mutableStateOf(ConfigSportState())
    val state: State<ConfigSportState> = _state

    init {
        getSportList()
    }

    fun onEvent(event: ConfigSportEvent) {
        when (event) {
//            is ConfigSportEvent.EnteredLastName -> {
//                _state.value = _state.value.copy(
//                    lastName = event.value
//                )
//            }
        }
    }


    private fun getSportList(){
        featRepository.getGenericsSports().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        ConfigSportState(
                            error = result.message
                                ?: resourcesProvider.getString(R.string.error_unknown)
                        )
                }
                is Result.Loading -> {
                    _state.value = ConfigSportState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ConfigSportState(sportsList = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}