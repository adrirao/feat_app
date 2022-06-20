package com.unlam.feat.presentation.view.search.event_detail


sealed class SearchEventDetailEvent {
    object DismissDialog: SearchEventDetailEvent()

    object ApplyEvent: SearchEventDetailEvent()
}