package com.unlam.feat.presentation.view.events.add_event


import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.Person
import com.unlam.feat.model.Sport
import com.unlam.feat.model.SportGeneric
import java.time.LocalDate
import java.time.LocalTime

data class AddEventState(
    val isCreatedMessage: String? = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val periodicityList: List<Periodicity> = listOf(),
    val sportGenericList:List<SportGeneric> = listOf(),
    val sportList:List<Sport> = listOf(),
    val address: String = " ",
    val person: Person? = null,
    val sportGeneric: String = "",

    val name: String = "",
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val description: String = "",
    val periodicity: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val state: String = "1",
    val sport: String = "",
    )