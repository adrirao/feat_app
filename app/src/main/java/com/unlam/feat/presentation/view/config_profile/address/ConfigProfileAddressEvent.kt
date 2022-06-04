package com.unlam.feat.presentation.view.config_profile.address



sealed class ConfigProfileAddressEvent {
    data class EnteredAddressAlias(val value: String) : ConfigProfileAddressEvent()
    data class EnteredAddressStreet(val value: String) : ConfigProfileAddressEvent()
    data class EnteredAddressNumber(val value: String) : ConfigProfileAddressEvent()
    data class EnteredAddressTown(val value: String) : ConfigProfileAddressEvent()
    data class EnteredAddressZipCode(val value: String) : ConfigProfileAddressEvent()
    data class EnteredAddressLatitude(val value: String) : ConfigProfileAddressEvent()
    data class EnteredAddressLongitude(val value: String) : ConfigProfileAddressEvent()
    data class ShowAlertPermission(val value: Boolean,val title:String,val description:String): ConfigProfileAddressEvent()
    object DismissDialog: ConfigProfileAddressEvent()

}