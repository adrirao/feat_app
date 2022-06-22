package com.unlam.feat.presentation.view.edit_profile

import com.unlam.feat.presentation.view.config_profile.additional_information.ConfigProfileAdditionalInformationEvent
import com.unlam.feat.presentation.view.events.add_event.AddEventEvent
import java.time.LocalDate

sealed class ProfileEvent {
    object DismissDialog : ProfileEvent()
}