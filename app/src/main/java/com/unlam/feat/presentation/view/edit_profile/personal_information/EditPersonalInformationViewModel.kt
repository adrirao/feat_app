package com.unlam.feat.presentation.view.edit_profile.personal_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.Person
import com.unlam.feat.model.request.RequestUpdatePersonPersonalInformation
import com.unlam.feat.presentation.view.edit_profile.ProfileEvent
import com.unlam.feat.presentation.view.edit_profile.ProfileState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EditPersonalInformationViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(EditPersonalInformationState())
    val state: State<EditPersonalInformationState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getPerson()
    }

    fun onEvent(event: EditProfilePersonalInformationEvent) {
        when (event) {
            is EditProfilePersonalInformationEvent.EnteredNames -> {
                _state.value = _state.value.copy(
                    names = event.value
                )
            }
            is EditProfilePersonalInformationEvent.EnteredLastNames -> {
                _state.value = _state.value.copy(
                    lastname = event.value
                )
            }
            is EditProfilePersonalInformationEvent.EnteredBirthDate -> {
                _state.value = _state.value.copy(
                    birth_date = event.value
                )
            }
            is EditProfilePersonalInformationEvent.EnteredSex -> {
                _state.value = _state.value.copy(
                    sex = event.value
                )
            }
            is EditProfilePersonalInformationEvent.EnteredNickname -> {
                _state.value = _state.value.copy(
                    nickname = event.value
                )
            }
        }
    }

    fun updatePerson(){

        val request = RequestUpdatePersonPersonalInformation(
            id= _state.value.person!!.id,
            names = _state.value.names,
            lastname = _state.value.lastname,
            birthDate = _state.value.birth_date.toString(),
            sex = _state.value.sex,
            nickname = _state.value.nickname,
        )

        featRepository.updatePersonPersonalInformation(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = EditPersonalInformationState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = EditPersonalInformationState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EditPersonalInformationState(isUpdatedMessage = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPerson(){

        val uId = firebaseAuthRepository.getUserId();
        featRepository.getDetailProfile(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        EditPersonalInformationState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = EditPersonalInformationState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EditPersonalInformationState(
                        person = result.data!!.person,
                        names = result.data.person.names,
                        lastname = result.data.person.lastname,
                        nickname = result.data.person.nickname,
                        birth_date = LocalDate.parse(result.data.person.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")),
                        sex = result.data.person.sex)
                }
            }
        }.launchIn(viewModelScope)
    }
}