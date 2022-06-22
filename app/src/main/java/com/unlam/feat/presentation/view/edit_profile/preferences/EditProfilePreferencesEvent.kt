package com.unlam.feat.presentation.view.edit_profile.preferences

import com.unlam.feat.presentation.view.edit_profile.ProfileEvent

sealed class EditProfilePreferencesEvent {
    data class EnteredMinAge(val value: String) : EditProfilePreferencesEvent()
    data class EnteredMaxAge(val value: String) : EditProfilePreferencesEvent()
    data class EnteredNotifications(val value: Boolean) : EditProfilePreferencesEvent()
    data class EnteredWillingDistance(val value: String) : EditProfilePreferencesEvent()
}