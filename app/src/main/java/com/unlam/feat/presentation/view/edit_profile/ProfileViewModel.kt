package com.unlam.feat.presentation.view.edit_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.di.ResourcesProvider
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
        getPersonByUser()
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
                }
            }
        }.launchIn(viewModelScope)
    }

}