package com.unlam.feat.presentation.view.config_profile.personal_data

import java.time.LocalDate


data class ConfigProfilePersonalDataState(
    val lastName: String = "",
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val nickname: String = "",
    val sex: String = "",
)