package com.unlam.feat.presentation.view.config_profile


import java.time.LocalDate

sealed class ConfigProfileEvent {
    data class EnteredLastName(val value: String) : ConfigProfileEvent()
    data class EnteredName(val value: String) : ConfigProfileEvent()
    data class EnteredDateOfBirth(val value: LocalDate) : ConfigProfileEvent()
    data class EnteredNickname(val value: String) : ConfigProfileEvent()
    data class EnteredSex(val value: String) : ConfigProfileEvent()
    data class EnteredAddressAlias(val value: String) : ConfigProfileEvent()
    data class EnteredAddressStreet(val value: String) : ConfigProfileEvent()
    data class EnteredAddressNumber(val value: String) : ConfigProfileEvent()
    data class EnteredAddressTown(val value: String) : ConfigProfileEvent()
    data class EnteredAddressZipCode(val value: String) : ConfigProfileEvent()
    data class EnteredAddressLatitude(val value: String) : ConfigProfileEvent()
    data class EnteredAddressLongitude(val value: String) : ConfigProfileEvent()
    data class ShowAlertPermission(val value: Boolean,val title:String,val description:String): ConfigProfileEvent()
    object DismissDialog: ConfigProfileEvent()

}