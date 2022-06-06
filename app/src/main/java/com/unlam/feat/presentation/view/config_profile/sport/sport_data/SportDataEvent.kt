package com.unlam.feat.presentation.view.config_profile.sport.sport_data



sealed class SportDataEvent {
    data class EnteredName(val value : String) : SportDataEvent()
}