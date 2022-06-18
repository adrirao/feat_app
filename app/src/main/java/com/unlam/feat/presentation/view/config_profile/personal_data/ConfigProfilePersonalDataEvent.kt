package com.unlam.feat.presentation.view.config_profile.personal_data



import java.time.LocalDate

sealed class ConfigProfilePersonalDataEvent {
    data class EnteredLastName(val value: String) : ConfigProfilePersonalDataEvent()
    data class EnteredName(val value: String) : ConfigProfilePersonalDataEvent()
    data class EnteredDateOfBirth(val value: LocalDate) : ConfigProfilePersonalDataEvent()
    data class EnteredNickname(val value: String) : ConfigProfilePersonalDataEvent()
    data class EnteredSex(val value: String) : ConfigProfilePersonalDataEvent()
    object DismissDialog: ConfigProfilePersonalDataEvent()
    object SingOutUser: ConfigProfilePersonalDataEvent()
    object SubmitData: ConfigProfilePersonalDataEvent()
}