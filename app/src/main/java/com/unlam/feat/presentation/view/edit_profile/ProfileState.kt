package com.unlam.feat.presentation.view.edit_profile

import com.unlam.feat.model.Address
import com.unlam.feat.model.Person
import com.unlam.feat.model.Player
import com.unlam.feat.model.SportGeneric


data class ProfileState (
    val isLoading: Boolean = false,
    val error: String = "",
    val person: Person? = null,
    val addresses: List<Address>? = emptyList(),
    val players: List<Player>? = emptyList()
)