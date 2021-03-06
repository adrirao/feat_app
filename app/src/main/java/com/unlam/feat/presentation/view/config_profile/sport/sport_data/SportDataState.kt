package com.unlam.feat.presentation.view.config_profile.sport.sport_data

import com.unlam.feat.model.Level
import com.unlam.feat.model.Person
import com.unlam.feat.model.Position
import com.unlam.feat.model.Valuation

data class SportDataState(
    val error: String = "",
    val isLoading:Boolean = false,
    val isSuccessSubmitData:Boolean = false,
    val abilitiesError:Boolean = false,
    val positionIdError:Boolean = false,
    val levelIdError:Boolean = false,
    val valuationIdError:Boolean = false,
    val fieldEmpty:String = "",


    val person: Person? = null,
    val positionList: List<Position>  = emptyList() ,
    val levelList: List<Level> = emptyList(),
    val valuationList: List<Valuation> = emptyList(),

    val abilities:String = "",
    val sportGenericId:Int? = null,
    val positionId:Int? = null,
    val levelId:Int? = null,
    val valuationId:Int? = null,

)