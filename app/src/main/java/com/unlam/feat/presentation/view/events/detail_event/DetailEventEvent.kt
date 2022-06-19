package com.unlam.feat.presentation.view.events.detail_event

import com.unlam.feat.presentation.view.invitation.detail_invitation.DetailInvitationEvent

sealed class DetailEventEvent {
    object DismissDialog : DetailEventEvent()
}