package com.unlam.feat.presentation.view.config_profile.personal_data

import com.unlam.feat.presentation.view.register.RegisterState
import java.time.LocalDate


data class ConfigProfilePersonalDataState(
    val lastName: String = "",
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val nickname: String = "",
    val sex: String = "",

    val isLoading:Boolean = false,
    val nameError: NameError? = null,
    val lastNameError: LastNameError? = null,
    val sexError: SexError? = null,
    val dateOfBirthError: DateOfBirthError? = null,
    val error:String? = null,
    val isSuccessSubmitData:Boolean = false,
    val fieldEmpty:String = ""
) {
//TODO refactorizar si solo se va a validar si el campo esta vacio o no.

    sealed class NameError {
        object FieldEmpty : NameError()
    }

    sealed class LastNameError {
        object FieldEmpty : LastNameError()
    }

    sealed class SexError {
        object FieldEmpty : SexError()
    }

    sealed class DateOfBirthError {
        object IsInvalid : DateOfBirthError()
        object FieldEmpty : DateOfBirthError()
    }


}