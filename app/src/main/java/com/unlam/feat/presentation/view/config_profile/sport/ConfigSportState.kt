package com.unlam.feat.presentation.view.config_profile.sport

import com.unlam.feat.model.SportGeneric

data class ConfigSportState(
    val error: String = "",
    val sportsList: List<SportGeneric> = emptyList(),
    val isLoading: Boolean = false,

    val soccerCardEnable: Boolean = true,
    val basketballCardEnable: Boolean = true,
    val tennisCardEnable: Boolean = true,
    val padelCardEnable: Boolean = true,
    val recreationalActivityCardEnable: Boolean = true,

)