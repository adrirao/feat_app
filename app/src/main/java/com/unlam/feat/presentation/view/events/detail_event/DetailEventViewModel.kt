package com.unlam.feat.presentation.view.events.detail_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlam.feat.common.Result
import com.unlam.feat.model.request.RequestCreateInvitation
import com.unlam.feat.model.request.RequestEventApply
import com.unlam.feat.model.request.RequestEventState
import com.unlam.feat.repository.FeatRepositoryImp
import com.unlam.feat.repository.FirebaseAuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailEventViewModel
@Inject
constructor(
    val featRepository: FeatRepositoryImp,
    val firebaseAuthRepository: FirebaseAuthRepositoryImp
) : ViewModel() {
    private val _state = mutableStateOf(DetailEventState())
    val state: State<DetailEventState> = _state


    fun onEvent(event: DetailEventEvent) {
        when (event) {
            is DetailEventEvent.DismissDialog -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "",
                    successPlayer = false,
                    successConfirmEvent = false
                )
            }
            is DetailEventEvent.CancelEvent -> {
                cancelEvent()
            }
            is DetailEventEvent.ConfirmEvent -> {
                confirmEvent()
            }
            is DetailEventEvent.KickPlayer -> {
                kickPlayer()
            }
            is DetailEventEvent.RejectPlayer -> {
                rejectPlayer()
            }
            is DetailEventEvent.AcceptPlayer -> {
                acceptPlayer()
            }
            is DetailEventEvent.InvitePlayer -> {
                invitePlayer()
            }

        }
    }

    private fun kickPlayer() {

    }


    private fun invitePlayer() {
        val uid = firebaseAuthRepository.getUserId()

        val requestCreateInvitation = RequestCreateInvitation(
            playerId = uid,
            eventId = _state.value.event!!.id,
            origin = "O"
        )

        featRepository.createInvitation(requestCreateInvitation).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Invitacion enviada",
                        successDescription = "La invitacion se ha enviado con exito",
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


    private fun rejectPlayer() {
        val uid = firebaseAuthRepository.getUserId()
        val requestEventApply = RequestEventApply(
            userUid = uid,
            eventId = state.value.event!!.id
        )

        featRepository.setDeniedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Jugador rechazado",
                        successDescription = "El jugador ha sido rechazado con exito",
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

   private fun acceptPlayer() {
        val uid = firebaseAuthRepository.getUserId()
        val requestEventApply = RequestEventApply(
            userUid = uid,
            eventId = state.value.event!!.id
        )

        featRepository.setAcceptedApply(requestEventApply).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successPlayer = true,
                        successTitle = "Jugador aceptado",
                        successDescription = "El jugador ha sido aceptado con exito",
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

    private fun confirmEvent() {
        val requestEventState = RequestEventState(
            id = state.value.event!!.id
        )
        featRepository.setConfirmed(requestEventState).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successConfirmEvent = true,
                        successTitle = "Evento confirmado",
                        successDescription = "El evento se ha confirmado con exito"
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

    private fun cancelEvent() {
        val requestEventState = RequestEventState(
            id = state.value.event!!.id
        )
        featRepository.setCanceled(requestEventState).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        successCancelEvent = true,
                        successTitle = "Evento cancelado",
                        successDescription = "El evento se ha cancelado con exito"
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


    fun refreshData() {
        val idEvent = state.value.event!!.id
        featRepository.getDataDetailEvent(idEvent).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        _state.value.copy(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        event = result.data!!.event,
                        playersApplied = result.data.playersApplied,
                        playersConfirmed = result.data.playersConfirmed,
                        playersSuggested = result.data.playersSuggested,
                        isLoading = false,
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
                        DetailEventState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DetailEventState(loading = true)
                }
                is Result.Success -> {
                    _state.value = DetailEventState(
                        event = result.data!!.event,
                        playersApplied = result.data.playersApplied,
                        playersConfirmed = result.data.playersConfirmed,
                        playersSuggested = result.data.playersSuggested
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}