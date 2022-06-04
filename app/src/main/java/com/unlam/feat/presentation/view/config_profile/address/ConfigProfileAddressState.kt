package com.unlam.feat.presentation.view.config_profile.address

import java.time.LocalDate
import java.time.LocalTime



data class ConfigProfileAddressState(
    val addressAlias: String = "",
    val addressStreet: String = "",
    val addressNumber: String = "",
    val addressTown: String = "",
    val addressZipCode: String = "",
    val addressLatitude: String = "",
    val addressLongitude: String = "",

    val showAlertPermission: Boolean = false,
    val titleAlert: String = "",
    val descriptionAlert: String = ""
)