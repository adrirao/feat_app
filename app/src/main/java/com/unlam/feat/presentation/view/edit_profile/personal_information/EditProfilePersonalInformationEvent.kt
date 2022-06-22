package com.unlam.feat.presentation.view.edit_profile.personal_information

import com.unlam.feat.presentation.view.edit_profile.ProfileEvent
import java.time.LocalDate

sealed class EditProfilePersonalInformationEvent {
    data class EnteredNames(val value : String) : EditProfilePersonalInformationEvent()
    data class EnteredLastNames(val value: String) : EditProfilePersonalInformationEvent()
    data class EnteredBirthDate(val value: LocalDate) : EditProfilePersonalInformationEvent()
    data class EnteredSex(val value: String) : EditProfilePersonalInformationEvent()
    data class EnteredNickname(val value: String) : EditProfilePersonalInformationEvent()
}