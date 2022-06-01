package com.unlam.feat.presentation.view.search

sealed class SearchEvent {
    object DismissDialog: SearchEvent()
}