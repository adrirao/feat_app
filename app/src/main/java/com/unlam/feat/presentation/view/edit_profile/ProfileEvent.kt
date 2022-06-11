package com.unlam.feat.presentation.view.edit_profile

import com.unlam.feat.presentation.view.events.EventEvent

sealed class ProfileEvent {
    object DismissDialog : ProfileEvent()
}