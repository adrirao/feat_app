package com.unlam.feat.presentation.view.events.detail_event


sealed class DetailEventEvent {
    object DismissDialog : DetailEventEvent()

    object CancelEvent: DetailEventEvent()
    object ConfirmEvent: DetailEventEvent()

    object KickPlayer: DetailEventEvent()

    object RejectPlayer: DetailEventEvent()
    object AcceptPlayer: DetailEventEvent()

    object InvitePlayer: DetailEventEvent()

}