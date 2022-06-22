package com.unlam.feat.presentation.view.invitation.detail_invitation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestEventApply
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DetailInvitationViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp,
    val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailInvitationState())
    val state: State<DetailInvitationState> = _state


    fun onEvent(event: DetailInvitationEvent) {
        when (event) {
            is DetailInvitationEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    error = "",
                    loading = false
                )
            }
            is DetailInvitationEvent.ConfirmInvitation -> {
                confirmInvitation()
            }
            is DetailInvitationEvent.CancelInvitation -> {
                cancelInvitation()
            }
        }
    }


  private  fun confirmInvitation() {
        val uid = firebaseAuthRepository.getUserId()
        val requestEventApply = RequestEventApply(
            playerId = uid,
            eventId = state.value.event!!.id
        )

        featRepository.setAcceptedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        success = true,
                        successTitle = "Invitacion aceptada",
                        successDescription = "La invitacion se ha aceptado con exito",
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        loading = true
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


    private fun cancelInvitation() {
        val uid = firebaseAuthRepository.getUserId()
        val requestEventApply = RequestEventApply(
            playerId = uid,
            eventId = state.value.event!!.id
        )

        featRepository.setDeniedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        success = true,
                        successTitle = "Invitacion cancelada",
                        successDescription = "La invitacion se cancelo con exito"
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(
                        loading = true
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

     fun getDataDetailEvent(idEvent: Int) {
        featRepository.getDataDetailEvent(idEvent).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        DetailInvitationState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailInvitationState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailInvitationState(
                        event = result.data!!.event,
                        playersConfirmed = result.data.playersConfirmed,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}