package com.unlam.feat.presentation.view.config_profile.additional_information

data class ConfigProfileAdditionalInformationState(
    val minAge: String = "",
    val maxAge: String = "",
    val notifications: Boolean = false,
    val willingDistance: String = "1",
    val personId: Int? = null,

    val ageError: RangeAgeError? = null,
    val error: String? = null,
    val personError: String? = null,
    val isLoading:Boolean = false,
    val isSuccessSubmitData:Boolean = false,
) {

    sealed class RangeAgeError {
        object IsInvalidRange : RangeAgeError()
        object MinAgeEmpty : RangeAgeError()
        object MaxAgeEmpty : RangeAgeError()
        object FieldEmpty : RangeAgeError()
    }
}