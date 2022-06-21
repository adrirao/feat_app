package com.unlam.feat.presentation.view.edit_profile.address

import com.unlam.feat.presentation.view.config_profile.address.ConfigProfileAddressEvent

sealed class EditProfileAddressEvent{
    data class EnteredAddressAlias(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressStreet(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressNumber(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressTown(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressZipCode(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressLatitude(val value: String) : EditProfileAddressEvent()
    data class EnteredAddressLongitude(val value: String) : EditProfileAddressEvent()
    data class ShowAlertPermission(val value: Boolean,val title:String,val description:String): EditProfileAddressEvent()
    object DismissDialog: EditProfileAddressEvent()
    object SubmitData: EditProfileAddressEvent()
    object SingOutUser: EditProfileAddressEvent()
}