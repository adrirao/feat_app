package com.unlam.feat.presentation.view.config_profile.availability

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestAvailability
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class ConfigProfileAvailabilityViewModel @Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(ConfigProfileAvailabilityState())
    val state: State<ConfigProfileAvailabilityState> = _state

    init {
        val uId = firebaseAuthRepository.getUserId()
        getPerson(uId)
    }


    fun onEvent(event: ConfigProfileAvailabilityEvent) {
        when (event) {
            is ConfigProfileAvailabilityEvent.EnteredStartTime1 -> {
                _state.value = _state.value.copy(
                    startTimeSunday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime1 -> {
                _state.value = _state.value.copy(
                    endTimeSunday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime2 -> {
                _state.value = _state.value.copy(
                    startTimeMonday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime2 -> {
                _state.value = _state.value.copy(
                    endTimeMonday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime3 -> {
                _state.value = _state.value.copy(
                    startTimeTuesday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime3 -> {
                _state.value = _state.value.copy(
                    endTimeTuesday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime4 -> {
                _state.value = _state.value.copy(
                    startTimeWednesday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime4 -> {
                _state.value = _state.value.copy(
                    endTimeWednesday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime5 -> {
                _state.value = _state.value.copy(
                    startTimeThursday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime5 -> {
                _state.value = _state.value.copy(
                    endTimeThursday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime6 -> {
                _state.value = _state.value.copy(
                    startTimeFriday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime6 -> {
                _state.value = _state.value.copy(
                    endTimeFriday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredStartTime7 -> {
                _state.value = _state.value.copy(
                    startTimeSaturday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.EnteredEndTime7 -> {
                _state.value = _state.value.copy(
                    endTimeSaturday = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.SundayIsChecked -> {
                _state.value = _state.value.copy(
                    sundayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.MondayIsChecked -> {
                _state.value = _state.value.copy(
                    mondayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.TuesdayIsChecked -> {
                _state.value = _state.value.copy(
                    tuesdayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.WednesdayIsChecked -> {
                _state.value = _state.value.copy(
                    wednesdayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.ThursdayIsChecked -> {
                _state.value = _state.value.copy(
                    thursdayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.FridayIsChecked -> {
                _state.value = _state.value.copy(
                    fridayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.SaturdayIsChecked -> {
                _state.value = _state.value.copy(
                    saturdayIsChecked = event.value
                )
            }
            is ConfigProfileAvailabilityEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    allDayEmpty = false,
                    error = null,
                    sundayError = null,
                    mondayError = null,
                    tuesdayError = null,
                    wednesdayError = null,
                    thursdayError = null,
                    fridayError = null,
                    saturdayError = null,
                    fieldError = ""
                )

            }
            is ConfigProfileAvailabilityEvent.SingOutUser -> {
                firebaseAuthRepository.signOut()
            }
            is ConfigProfileAvailabilityEvent.SubmitData -> {
                _state.value = _state.value.copy(
                    sundayError = validateDayIsValidRange(
                        state.value.startTimeSunday,
                        state.value.endTimeSunday,
                        state.value.sundayIsChecked
                    ),
                    mondayError = validateDayIsValidRange(
                        state.value.startTimeMonday,
                        state.value.endTimeMonday,
                        state.value.mondayIsChecked
                    ),
                    tuesdayError = validateDayIsValidRange(
                        state.value.startTimeTuesday,
                        state.value.endTimeTuesday,
                        state.value.tuesdayIsChecked
                    ),
                    wednesdayError = validateDayIsValidRange(
                        state.value.startTimeWednesday,
                        state.value.endTimeWednesday,
                        state.value.wednesdayIsChecked
                    ),
                    thursdayError = validateDayIsValidRange(
                        state.value.startTimeThursday,
                        state.value.endTimeThursday,
                        state.value.thursdayIsChecked
                    ),
                    fridayError = validateDayIsValidRange(
                        state.value.startTimeFriday,
                        state.value.endTimeFriday,
                        state.value.fridayIsChecked
                    ),
                    saturdayError = validateDayIsValidRange(
                        state.value.startTimeSaturday,
                        state.value.endTimeSaturday,
                        state.value.saturdayIsChecked
                    )
                )
                validateField()
                addAvailabilities()


            }

        }
    }

    private fun addAvailabilities() {
        val personId = if (state.value.personId != null) _state.value.personId else return

        val startTime1 = if (state.value.sundayError == null) {
            if (state.value.startTimeSunday != null) _state.value.startTimeSunday.toString() else null
        } else return
        val endTime1 = if (state.value.sundayError == null) {
            if (state.value.endTimeSunday != null) _state.value.endTimeSunday.toString() else null
        } else return
        val startTime2 = if (state.value.mondayError == null) {
            if (state.value.startTimeMonday != null) _state.value.startTimeMonday.toString() else null
        } else return
        val endTime2 = if (state.value.mondayError == null) {
            if (state.value.endTimeMonday != null) _state.value.endTimeMonday.toString() else null
        } else return
        val startTime3 = if (state.value.tuesdayError == null) {
            if (state.value.startTimeTuesday != null) _state.value.startTimeTuesday.toString() else null
        } else return
        val endTime3 = if (state.value.tuesdayError == null) {
            if (state.value.endTimeTuesday != null) _state.value.endTimeTuesday.toString() else null
        } else return
        val startTime4 = if (state.value.wednesdayError == null) {
            if (state.value.startTimeWednesday != null) _state.value.startTimeWednesday.toString() else null
        } else return
        val endTime4 = if (state.value.wednesdayError == null) {
            if (state.value.endTimeWednesday != null) _state.value.endTimeWednesday.toString() else null
        } else return
        val startTime5 = if (state.value.thursdayError == null) {
            if (state.value.startTimeThursday != null) _state.value.startTimeThursday.toString() else null
        } else return
        val endTime5 = if (state.value.thursdayError == null) {
            if (state.value.endTimeThursday != null) _state.value.endTimeThursday.toString() else null
        } else return
        val startTime6 = if (state.value.fridayError == null) {
            if (state.value.startTimeFriday != null) _state.value.startTimeFriday.toString() else null
        } else return
        val endTime6 = if (state.value.fridayError == null) {
            if (state.value.endTimeFriday != null) _state.value.endTimeFriday.toString() else null
        } else return
        val startTime7 = if (state.value.saturdayError == null) {
            if (state.value.startTimeSaturday != null) _state.value.startTimeSaturday.toString() else null
        } else return
        val endTime7 = if (state.value.saturdayError == null) {
            if (state.value.endTimeSaturday != null) _state.value.endTimeSaturday.toString() else null
        } else return

        if (
            startTime1.isNullOrBlank() && endTime1.isNullOrBlank() &&
            startTime2.isNullOrBlank() && endTime2.isNullOrBlank() &&
            startTime3.isNullOrBlank() && endTime3.isNullOrBlank() &&
            startTime4.isNullOrBlank() && endTime4.isNullOrBlank() &&
            startTime5.isNullOrBlank() && endTime5.isNullOrBlank() &&
            startTime6.isNullOrBlank() && endTime6.isNullOrBlank() &&
            startTime6.isNullOrBlank() && endTime7.isNullOrBlank()
        ) {
            if(state.value.sundayIsChecked || state.value.mondayIsChecked ||
               state.value.tuesdayIsChecked || state.value.wednesdayIsChecked ||
               state.value.thursdayIsChecked || state.value.fridayIsChecked ||
               state.value.saturdayIsChecked )  {
                return
            }

            _state.value = _state.value.copy(
                allDayEmpty = true
            )
            return
        }


        val request = RequestAvailability(
            startTime1 = startTime1,
            endTime1 = endTime1,
            startTime2 = startTime2,
            endTime2 = endTime2,
            startTime3 = startTime3,
            endTime3 = endTime3,
            startTime4 = startTime4,
            endTime4 = endTime4,
            startTime5 = startTime5,
            endTime5 = endTime5,
            startTime6 = startTime6,
            endTime6 = endTime6,
            startTime7 = startTime7,
            endTime7 = endTime7,
            person = requireNotNull(personId)
        )

        featRepository.createAvailability(request).onEach { result ->
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

    private fun getPerson(uId: String) {
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = ConfigProfileAvailabilityState(personId = result.data?.id)

                }
                is Result.Loading -> {
                    _state.value = ConfigProfileAvailabilityState(isLoading = true)

                }
                is Result.Error -> {
                    _state.value = ConfigProfileAvailabilityState(
                        personError = result.message ?: "Error Inesperado"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateField() {

        var fieldError: String = ""

        if (state.value.sundayError != null) fieldError += "Domingo, "
        if (state.value.mondayError != null) fieldError += "Lunes, "
        if (state.value.tuesdayError != null) fieldError += "Martes, "
        if (state.value.wednesdayError != null) fieldError += "Miercoles, "
        if (state.value.thursdayError != null) fieldError += "Jueves, "
        if (state.value.fridayError != null) fieldError += "Viernes, "
        if (state.value.saturdayError != null) fieldError += "Sabado, "

        if (fieldError != "") {
            _state.value = _state.value.copy(
                fieldError = fieldError
            )
        }
    }

    private fun validateDayIsValidRange(
        dayStar: LocalTime?,
        dayEnd: LocalTime?,
        dayIsChecked: Boolean
    ): ConfigProfileAvailabilityState.DayError? {

        if (dayIsChecked) {
            if (dayEnd == null && dayStar == null) {

                return ConfigProfileAvailabilityState.DayError.TimeEmpty
            }

            if (dayStar == null) {
                return ConfigProfileAvailabilityState.DayError.StarTimeEmpty
            }
            if (dayEnd == null) {

                return ConfigProfileAvailabilityState.DayError.EndTimeEmpty
            }

            if (dayStar.isAfter(dayEnd)) {
                return ConfigProfileAvailabilityState.DayError.WrongTimeRange
            }
        }
        return null
    }


}


