package com.unlam.feat.presentation.view.configProfile

import java.time.LocalDate


data class ConfigProfileState(
    val lastName: String = "",
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val nickname: String = "",
    val sex: String = "",

//    val addrress:List<String>? = null,
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