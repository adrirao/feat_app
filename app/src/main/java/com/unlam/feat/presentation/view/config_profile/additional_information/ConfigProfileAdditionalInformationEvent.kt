package com.unlam.feat.presentation.view.config_profile.additional_information

import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent

sealed class ConfigProfileAdditionalInformationEvent {
    data class EnteredMinAge(val value: String) : ConfigProfileAdditionalInformationEvent()
    data class EnteredMaxAge(val value: String) : ConfigProfileAdditionalInformationEvent()
    data class EnteredNotifications(val value: Boolean) : ConfigProfileAdditionalInformationEvent()
    data class EnteredWillingDistance(val value: String) : ConfigProfileAdditionalInformationEvent()
    object DismissDialog: ConfigProfileAdditionalInformationEvent()
    object SingOutUser: ConfigProfileAdditionalInformationEvent()
    object SubmitData: ConfigProfileAdditionalInformationEvent()
}