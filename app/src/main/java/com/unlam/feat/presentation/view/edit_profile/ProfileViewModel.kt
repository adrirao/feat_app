package com.unlam.feat.presentation.view.edit_profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
import com.unlam.feat.presentation.view.events.detail_event.DetailEventState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            is ProfileEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = ""
                )
            }
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
                        addresses = result.data.addresses,
                        players = result.data.players
                    )
                    Log.d("Person", _state.value.players.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
/*
    fun getPersonByUser() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getPerson(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ProfileState(person = (result.data))
                    Log.d("Person", _state.value.person.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAddressesByUser() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getAddressesByUser(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ProfileState(addresses = (result.data))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPlayersByUser() {
        val uId = firebaseAuthRepository.getUserId()
        featRepository.getPlayersByUser(uId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = ProfileState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = ProfileState(players = (result.data))
                }
            }
        }.launchIn(viewModelScope)
    }

 */

}