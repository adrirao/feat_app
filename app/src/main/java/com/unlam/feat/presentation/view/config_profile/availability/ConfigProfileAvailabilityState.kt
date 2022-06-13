package com.unlam.feat.presentation.view.config_profile.availability


import java.time.LocalTime

data class ConfigProfileAvailabilityState(

    val startTimeSunday: LocalTime? = null,
    val endTimeSunday: LocalTime? = null,

    val startTimeMonday: LocalTime? = null,
    val endTimeMonday: LocalTime? = null,

    val startTimeTuesday: LocalTime? = null,
    val endTimeTuesday: LocalTime? = null,

    val startTimeWednesday: LocalTime? = null,
    val endTimeWednesday: LocalTime? = null,

    val startTimeThursday: LocalTime? = null,
    val endTimeThursday: LocalTime? = null,

    val startTimeFriday: LocalTime? = null,
    val endTimeFriday: LocalTime? = null,

    val startTimeSaturday: LocalTime? = null,
    val endTimeSaturday: LocalTime? = null,

    val personId: Int? = null,

    val sundayIsChecked: Boolean = false,
    val mondayIsChecked: Boolean = false,
    val tuesdayIsChecked: Boolean = false,
    val wednesdayIsChecked: Boolean = false,
    val thursdayIsChecked: Boolean = false,
    val fridayIsChecked: Boolean = false,
    val saturdayIsChecked: Boolean = false,

    val isLoading: Boolean = false,
    val isSuccessSubmitData: Boolean = false,
    val error: String? = null,

    val personError: String = "",
    val sundayError: DayError? = null,
    val mondayError: DayError? = null,
    val tuesdayError: DayError? = null,
    val wednesdayError: DayError? = null,
    val thursdayError: DayError? = null,
    val fridayError: DayError? = null,
    val saturdayError: DayError? = null,
    val allDayEmpty:Boolean = false,
    val fieldError:String = ""


    ) {
    sealed class DayError {
        object WrongTimeRange : DayError()
        object StarTimeEmpty : DayError()
        object EndTimeEmpty : DayError()
        object TimeEmpty : DayError()
    }

}