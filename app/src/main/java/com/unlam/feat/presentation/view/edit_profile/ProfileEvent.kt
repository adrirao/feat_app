package com.unlam.feat.presentation.view.edit_profile

import com.unlam.feat.presentation.view.config_profile.additional_information.ConfigProfileAdditionalInformationEvent
import com.unlam.feat.presentation.view.events.add_event.AddEventEvent
import java.time.LocalDate

sealed class ProfileEvent {
    data class EnteredNames(val value : String) : ProfileEvent()
    data class EnteredLastNames(val value: String) : ProfileEvent()
    data class EnteredBirthDate(val value: LocalDate) : ProfileEvent()
    data class EnteredSex(val value: String) : ProfileEvent()
    data class EnteredNickname(val value: String) : ProfileEvent()
    object DismissDialog : ProfileEvent()
}