package com.unlam.feat.presentation.view.edit_profile

import com.unlam.feat.model.Person


data class ProfileState (
    val isLoading: Boolean = false,
    val error: String = "",
    val person: Person? = null
)