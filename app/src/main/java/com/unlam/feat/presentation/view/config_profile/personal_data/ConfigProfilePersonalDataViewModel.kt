package com.unlam.feat.presentation.view.config_profile.personal_data


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestPerson
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ConfigProfilePersonalDataViewModel
@Inject
constructor(
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp,
    private val featRepository: FeatRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(ConfigProfilePersonalDataState())
    val state: State<ConfigProfilePersonalDataState> = _state

    fun onEvent(event: ConfigProfilePersonalDataEvent) {
        when (event) {
            is ConfigProfilePersonalDataEvent.EnteredLastName -> {
                _state.value = _state.value.copy(
                    lastName = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredName -> {
                _state.value = _state.value.copy(
                    name = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredDateOfBirth -> {
                _state.value = _state.value.copy(
                    dateOfBirth = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredNickname -> {
                _state.value = _state.value.copy(
                    nickname = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.EnteredSex -> {
                _state.value = _state.value.copy(
                    sex = event.value
                )
            }
            is ConfigProfilePersonalDataEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    nameError = null,
                    lastNameError = null,
                    sexError = null,
                    dateOfBirthError = null,
                    fieldEmpty = ""

                )

            }
            is ConfigProfilePersonalDataEvent.SingOutUser -> {
                firebaseAuthRepository.signOut()
            }
            is ConfigProfilePersonalDataEvent.SubmitData -> {

                validateNameIsNotNull(state.value.name)
                validateLastNameIsNotNull(state.value.lastName)
                validateSexIsNotNull(state.value.sex)
                validateDateIsNotNullAndValidDate(state.value.dateOfBirth)
                validateField()
                createPerson()
            }
        }
    }


    private fun createPerson() {
        val name = if (state.value.nameError == null) _state.value.name else return
        val lastName = if (state.value.lastNameError == null) _state.value.lastName else return
        val dateOfBirth =
            if (state.value.dateOfBirthError == null) _state.value.dateOfBirth else return
        val nickname = _state.value.nickname
        val sex = if (state.value.sexError == null) _state.value.sex else return
        val uId = firebaseAuthRepository.getUserId()
        val request = RequestPerson(
            lastname = lastName,
            names = name,
            birthDate = dateOfBirth.toString(),
            sex = sex,
            nickname = nickname,
            userUid = uId
        )

        featRepository.createPerson(request).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isSuccessSubmitData = true
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message!!
                    )
                }
            }
        }.launchIn(viewModelScope)

    }


    private fun validateNameIsNotNull(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            _state.value = _state.value.copy(
                nameError = ConfigProfilePersonalDataState.NameError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(nameError = null)

    }

    private fun validateLastNameIsNotNull(lastName: String) {
        val trimmedLastName = lastName.trim()
        if (trimmedLastName.isBlank()) {
            _state.value = _state.value.copy(
                lastNameError = ConfigProfilePersonalDataState.LastNameError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(lastNameError = null)
    }

    private fun validateSexIsNotNull(sex: String) {
        val trimmedSex = sex.trim()
        if (trimmedSex.isBlank()) {
            _state.value = _state.value.copy(
                sexError = ConfigProfilePersonalDataState.SexError.FieldEmpty
            )
            return
        }
        _state.value = _state.value.copy(sexError = null)
    }

    private fun validateDateIsNotNullAndValidDate(dateOfBirth: LocalDate?) {

        if (dateOfBirth == null) {
            _state.value = _state.value.copy(
                dateOfBirthError = ConfigProfilePersonalDataState.DateOfBirthError.FieldEmpty
            )
            return
        }
        if (!dateOfBirth.isBefore(LocalDate.now().minusYears(16))) {
            _state.value = _state.value.copy(
                dateOfBirthError = ConfigProfilePersonalDataState.DateOfBirthError.IsInvalid
            )
            return
        }
        _state.value = _state.value.copy(dateOfBirthError = null)
    }

    private fun validateField() {

        var fieldError: String = ""

        if (state.value.nameError == ConfigProfilePersonalDataState.NameError.FieldEmpty) fieldError += "Nombres, "
        if (state.value.lastNameError == ConfigProfilePersonalDataState.LastNameError.FieldEmpty) fieldError += "Apellido, "
        if (state.value.dateOfBirthError == ConfigProfilePersonalDataState.DateOfBirthError.FieldEmpty) fieldError += "Fecha de nacimiento, "
        if (state.value.sexError == ConfigProfilePersonalDataState.SexError.FieldEmpty) fieldError += "Sexo"

        if (fieldError != "") {
            _state.value = _state.value.copy(
                fieldEmpty = fieldError
            )
        }
    }
}



