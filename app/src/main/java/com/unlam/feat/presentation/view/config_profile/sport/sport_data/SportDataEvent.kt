package com.unlam.feat.presentation.view.config_profile.sport.sport_data



sealed class SportDataEvent {
    data class SelectedPosition(val value : Int) : SportDataEvent()
    data class SelectedLevel(val value : Int) : SportDataEvent()
    data class SelectedValuation(val value : Int) : SportDataEvent()
    data class EnteredAbilities(val value : String) : SportDataEvent()
    data class EnteredSportGenericId(val value : Int?) : SportDataEvent()
    object DismissDialog : SportDataEvent()
    object SingOutUser : SportDataEvent()
    object SubmitData : SportDataEvent()

}