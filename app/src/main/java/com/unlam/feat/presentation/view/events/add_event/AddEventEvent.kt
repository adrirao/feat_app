package com.unlam.feat.presentation.view.events.add_event

sealed class AddEventEvent {
    data class EnteredName(val value : String) : AddEventEvent()
}