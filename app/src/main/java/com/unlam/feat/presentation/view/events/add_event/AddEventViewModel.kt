package com.unlam.feat.presentation.view.events.add_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.R
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.request.RequestEvent
import com.unlam.feat.presentation.view.events.EventState
import com.unlam.feat.repository.FeatRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel
@Inject
constructor(
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(AddEventState())
    val state: State<AddEventState> = _state

    init {
        getPeriodicities()
    }

    fun onEvent(event: AddEventEvent) {
        when (event) {
            is AddEventEvent.EnteredName -> {
                _state.value = _state.value.copy(
                    name = event.value
                )
            }
            is AddEventEvent.EnteredDay -> {
                _state.value = _state.value.copy(
                    date = event.value
                )
            }
            is AddEventEvent.EnteredStartTime -> {
                _state.value = _state.value.copy(
                    startTime = event.value
                )
            }
            is AddEventEvent.EnteredEndTime -> {
                _state.value = _state.value.copy(
                    endTime = event.value
                )
            }
            is AddEventEvent.EnteredDescription -> {
                _state.value = _state.value.copy(
                    description = event.value
                )
            }
            is AddEventEvent.EnteredPeriodicity -> {
                _state.value = _state.value.copy(
                    periodicity = event.value
                )
            }
            is AddEventEvent.EnteredAddress -> {
                _state.value = _state.value.copy(
                    address = event.value
                )
            }
            is AddEventEvent.EnteredLatLong -> {
                _state.value = _state.value.copy(
                    latitude = event.lat,
                    longitude = event.long
                )
            }
            is AddEventEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    isCreatedMessage = null
                )
            }
        }
    }

    fun createEvent() {

        val request = RequestEvent(
            name = _state.value.name,
            date = _state.value.date.toString(),
            startTime = _state.value.startTime.toString(),
            endTime = _state.value.endTime.toString(),
            description = _state.value.description,
            latitude = _state.value.latitude,
            longitude = _state.value.longitude,
            sport = "2",
            state = _state.value.state,
            periodicity = _state.value.periodicity,
            organizer = _state.value.organizer,
        )

        featRepository.postEvent(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = AddEventState(
                        error = result.message ?: resourcesProvider.getString(
                            R.string.error_unknown
                        )
                    )
                }
                is Result.Loading -> {
                    _state.value = AddEventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = AddEventState(isCreatedMessage = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPeriodicities() {
        featRepository.getPeriodicities().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = AddEventState(
                        error = result.message ?: resourcesProvider.getString(
                            R.string.error_unknown
                        )
                    )
                }
                is Result.Loading -> {
                    _state.value = AddEventState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = AddEventState(periodicityList = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}