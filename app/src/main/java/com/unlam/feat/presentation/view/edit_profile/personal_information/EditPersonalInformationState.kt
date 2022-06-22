package com.unlam.feat.presentation.view.edit_profile.personal_information

import com.unlam.feat.model.Person
import java.time.LocalDate

data class EditPersonalInformationState (
    val isLoading: Boolean = false,
    val error: String = "",
    val isUpdatedMessage: String? = "",

    val person: Person? = null,
    val names: String = "",
    val lastname: String = "",
    val nickname: String = "",
    val birth_date: LocalDate? = null,
    val sex: String= "",
)