package com.unlam.feat.presentation.view.config_profile.sport.sport_data

import com.unlam.feat.model.Level
import com.unlam.feat.model.Person
import com.unlam.feat.model.Position
import com.unlam.feat.model.Valuation

data class SportDataState(
    val error: String = "",

    val isLoadingPerson:Boolean = false,
    val isLoadingPosition:Boolean = false,
    val isLoadingLevel:Boolean = false,
    val isLoadingValuation:Boolean = false,

    val person: Person? = null,
    val positionList: List<Position>  = emptyList() ,
    val levelList: List<Level> = emptyList(),
    val valuationList: List<Valuation> = emptyList(),



)