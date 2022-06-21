package com.unlam.feat.presentation.view.edit_profile

import com.unlam.feat.model.Address
import com.unlam.feat.model.Person
import com.unlam.feat.model.Player
import java.time.LocalDate


data class ProfileState (
    val isLoading: Boolean = false,
    val error: String = "",
    val isUpdatedMessage: String? = "",
    val buttonUpdatePersonalInformation: Boolean = false,

    val person: Person? = null,
    val addresses: List<Address>? = emptyList(),
    val players: List<Player>? = emptyList(),

    val names: String = "",
    val lastname: String = "",
    val nickname: String = "",
    val birth_date: LocalDate? = null,
    val sex: String= "",

    val minAge: String = "",
    val maxAge: String = "",
    val notifications: Boolean = false,
    val willingDistance: String = "1"



)