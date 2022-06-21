package com.unlam.feat.presentation.view.edit_profile

import android.app.Person
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.model.request.RequestUpdatePerson
import com.unlam.feat.model.request.RequestUpdatePersonPersonalInformation
import com.unlam.feat.presentation.view.events.add_event.AddEventEvent
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
class ProfileViewModel@Inject
constructor(
    private val resourcesProvider: ResourcesProvider,
    private val featRepository: FeatRepositoryImp,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getDetailProfile()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EnteredNames -> {
                _state.value = _state.value.copy(
                    names = event.value
                )
            }
            is ProfileEvent.EnteredLastNames -> {
                _state.value = _state.value.copy(
                    lastname = event.value
                )
            }
            is ProfileEvent.EnteredBirthDate -> {
                _state.value = _state.value.copy(
                    birth_date = event.value
                )
            }
            is ProfileEvent.EnteredSex -> {
                _state.value = _state.value.copy(
                    sex = event.value
                )
            }
            is ProfileEvent.EnteredNickname -> {
                _state.value = _state.value.copy(
                    nickname = event.value
                )
            }
            is ProfileEvent.EnteredMinAge -> {
                _state.value = _state.value.copy(
                    minAge = event.value
                )
            }
            is ProfileEvent.EnteredMaxAge -> {
                _state.value = _state.value.copy(
                    maxAge = event.value
                )
            }
            is ProfileEvent.EnteredWillingDistance -> {
                _state.value = _state.value.copy(
                    willingDistance = event.value
                )
            }
            is ProfileEvent.EnteredNotifications -> {
                _state.value = _state.value.copy(
                    notifications = event.value
                )
            }
            is ProfileEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
        }
        if(_state.value.names != _state.value.person?.names.toString() ||
            _state.value.lastname != _state.value.person?.lastname.toString() ||
            _state.value.nickname != _state.value.person?.nickname.toString() ||
            _state.value.sex != _state.value.person?.sex.toString() ||
            _state.value.birth_date.toString() != _state.value.person?.birthDate.toString().substring(0, 10)
        ){

            _state.value = _state.value.copy(
                buttonUpdatePersonalInformation = true

            )
        }else{
            _state.value = _state.value.copy(
                buttonUpdatePersonalInformation = false
            )
        }
    }

    fun getDetailProfile(){
        val uId = firebaseAuthRepository.getUserId();
        featRepository.getDetailProfile(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ProfileState(
                        person = result.data!!.person,
                        players = result.data.players,
                        addresses = result.data.addresses,
                        names = result.data.person.names,
                        lastname = result.data.person.lastname,
                        nickname = result.data.person.nickname,
                        birth_date = LocalDate.parse(result.data.person.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")),
                        sex = result.data.person.sex,
                        minAge = result.data.person.minAge.toString(),
                        maxAge = result.data.person.maxAge.toString(),
                        willingDistance = result.data.person.willingDistance.toString(),
                        notifications = result.data.person.notifications.toBoolean()
                    )
                }
            }
        }.launchIn(viewModelScope)
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
                    _state.value = ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ProfileState(isUpdatedMessage = result.data)
                }
            }
        }.launchIn(viewModelScope)
        getDetailProfile()
    }

    fun updatePersonPreferences(){
        var notification = 0
        if(_state.value.notifications){
            notification = 1
        }
        val request = RequestUpdatePerson(
            id= _state.value.person!!.id,
            minAge = _state.value.minAge.toInt(),
            maxAge = _state.value.maxAge.toInt(),
            willingDistance = _state.value.willingDistance.toInt(),
            notifications =  notification,
        )

        featRepository.updatePerson(request).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ProfileState(isUpdatedMessage = result.data)
                }
            }
        }.launchIn(viewModelScope)
        getDetailProfile()
    }

}