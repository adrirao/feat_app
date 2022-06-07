package com.unlam.feat.presentation.view.config_profile

import java.time.LocalDate
import java.time.LocalTime


data class ConfigProfileState(
    val lastName: String = "",
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val nickname: String = "",
    val sex: String = "",

    val addressAlias: String = "",
    val addressStreet: String = "",
    val addressNumber: String = "",
    val addressTown: String = "",
    val addressZipCode: String = "",
    val addressLatitude: String = "",
    val addressLongitude: String = "",

    val startTime1: LocalTime? = null,
    val endTime1: LocalTime? = null,
    val startTime2: LocalTime? = null,
    val endTime2: LocalTime? = null,
    val startTime3: LocalTime? = null,
    val endTime3: LocalTime? = null,
    val startTime4: LocalTime? = null,
    val endTime4: LocalTime? = null,
    val startTime5: LocalTime? = null,
    val endTime5: LocalTime? = null,
    val startTime6: LocalTime? = null,
    val endTime6: LocalTime? = null,
    val startTime7: LocalTime? = null,
    val endTime7: LocalTime? = null,



    val showAlertPermission: Boolean = false,
    val titleAlert: String = "",
    val descriptionAlert: String = ""
)