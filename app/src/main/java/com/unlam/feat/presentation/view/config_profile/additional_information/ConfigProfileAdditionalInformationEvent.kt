package com.unlam.feat.presentation.view.config_profile.additional_information

sealed class ConfigProfileAdditionalInformationEvent {
    data class EnteredMinAge(val value: String) : ConfigProfileAdditionalInformationEvent()
    data class EnteredMaxAge(val value: String) : ConfigProfileAdditionalInformationEvent()
    data class EnteredNotifications(val value: Boolean) : ConfigProfileAdditionalInformationEvent()
    data class EnteredWillingDistance(val value: String) : ConfigProfileAdditionalInformationEvent()
}