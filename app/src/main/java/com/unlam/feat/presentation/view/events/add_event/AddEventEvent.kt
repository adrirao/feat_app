package com.unlam.feat.presentation.view.events.add_event


import java.time.LocalDate
import java.time.LocalTime

sealed class AddEventEvent {
    data class EnteredName(val value : String) : AddEventEvent()
    data class EnteredDay(val value: LocalDate) : AddEventEvent()
    data class EnteredStartTime(val value: LocalTime) : AddEventEvent()
    data class EnteredEndTime(val value: LocalTime) : AddEventEvent()
    data class EnteredDescription(val value: String) : AddEventEvent()
    data class EnteredPeriodicity(val value: String) : AddEventEvent()
    data class EnteredAddress(val value: String) : AddEventEvent()
    data class EnteredLatLong(val lat: String,val long:String) : AddEventEvent()
    data class EnteredSportGeneric(val value:String) : AddEventEvent()
    data class EnteredSport(val value:String) : AddEventEvent()
    object DismissDialog : AddEventEvent()


}