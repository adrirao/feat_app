package com.unlam.feat.presentation.view.config_profile.additional_information

data class ConfigProfileAdditionalInformationState(
    val minAge: String = "",
    val maxAge: String = "",
    val notifications: Boolean? = null,
    val willingDistance: String = "1",
)