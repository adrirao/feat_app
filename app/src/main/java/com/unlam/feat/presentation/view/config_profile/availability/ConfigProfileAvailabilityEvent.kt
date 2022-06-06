package com.unlam.feat.presentation.view.config_profile.availability


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

}