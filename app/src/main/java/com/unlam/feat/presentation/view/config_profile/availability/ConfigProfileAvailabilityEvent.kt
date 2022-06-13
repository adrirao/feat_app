package com.unlam.feat.presentation.view.config_profile.availability


import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import java.time.LocalDate
import java.time.LocalTime


sealed class ConfigProfileAvailabilityEvent {
    data class EnteredStartTime1(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime1(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredStartTime2(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime2(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredStartTime3(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime3(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredStartTime4(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime4(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredStartTime5(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime5(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredStartTime6(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime6(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredStartTime7(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class EnteredEndTime7(val value: LocalTime?) : ConfigProfileAvailabilityEvent()
    data class SundayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    data class MondayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    data class TuesdayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    data class WednesdayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    data class ThursdayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    data class FridayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    data class SaturdayIsChecked(val value: Boolean) : ConfigProfileAvailabilityEvent()
    object DismissDialog : ConfigProfileAvailabilityEvent()
    object SingOutUser : ConfigProfileAvailabilityEvent()
    object SubmitData : ConfigProfileAvailabilityEvent()

}