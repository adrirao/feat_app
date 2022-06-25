package com.unlam.feat.presentation.view.events.detail_event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.model.PlayerApplyDetail
import com.unlam.feat.presentation.component.*


@Composable
fun DetailEventScreen(
    state: DetailEventState,
    onEvent: (DetailEventEvent) -> Unit,
    refreshData: () -> Unit,
    navigateToEvents: () -> Unit
) {

    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")
    val event = state.event
    val playerSuggested = state.playersSuggested
    val playersApplied = state.playersApplied
    val playersConfirmed = state.playersConfirmed


    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = "Ocurrio un error",
            descriptionContent = "No se pudo procesar la solicitud, por favor, vuelva a intentarlo",
            onDismiss = {
                onEvent(DetailEventEvent.DismissDialog)
            }
        )
    }
    if (state.successPlayer) {
        FeatAlertDialog(
            title = state.successTitle,
            descriptionContent = state.successDescription,
            onDismiss = {
                refreshData()
                onEvent(DetailEventEvent.DismissDialog)
            }
        )
    }
    if (state.successCancelEvent) {
        FeatAlertDialog(
            title = state.successTitle,
            descriptionContent = state.successDescription,
            onDismiss = {
                navigateToEvents()
                onEvent(DetailEventEvent.DismissDialog)
            }
        )
    }
    if (state.successConfirmEvent) {
        FeatAlertDialog(
            title = state.successTitle,
            descriptionContent = state.successDescription,
            onDismiss = {
                refreshData()
                onEvent(DetailEventEvent.DismissDialog)
            }
        )
    }


    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary),
        ) {
            Column() {
                FeatHeader("Descripcion del evento")
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index,
                            modifier = Modifier.background(MaterialTheme.colors.primary),
                            onClick = {
                                tabIndex = index
                            },
                            text = { Text(text = title) }
                        )
                    }
                }
            }

        }
        when (tabIndex) {
            0 -> FeatCardEventDetail(event!!) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    FeatButton(
                        modifier = Modifier
                            .fillMaxWidth(0.5F)
                            .padding(10.dp)
                            .height(60.dp),
                        textButton = "Cancelar",
                        colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                        onClick = {
                            onEvent(DetailEventEvent.CancelEvent)
                        },
                    )

                    if (state.event.state.id != 3) {
                        FeatButton(
                            textButton = "Confirmar",
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            onClick = { onEvent(DetailEventEvent.ConfirmEvent) },
                        )
                    }
                }
            }
            1 -> ParticipantsDetail(
                playerSuggested!!,
                playersApplied!!,
                playersConfirmed!!,
                onEvent,
                state.isLoading,
                event = event!!
            )
        }
    }

}


@Composable
fun ParticipantsDetail(
    playerSuggested: List<Player>,
    playerApplied: List<PlayerApplyDetail>,
    playerConfirmed: List<Player>,
    onEvent: (DetailEventEvent) -> Unit,
    isLoading: Boolean,
    event: Event
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Confirmados", "Postulados", "Sugeridos")

    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary),
        ) {
            Column() {
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index,
                            modifier = Modifier.background(MaterialTheme.colors.primary),
                            onClick = {
                                tabIndex = index
                            },
                            text = { Text(text = title) }
                        )
                    }
                }
            }
        }
        if (isLoading) {
            FeatCircularProgress()
        } else {
            when (tabIndex) {
                0 -> PlayersConfirmed(playerConfirmed, event = event, onEvent = { onEvent(it) })
                1 -> PlayersApplied(playerApplied, event = event, onEvent = { onEvent(it) })
                2 -> PlayersSuggested(playerSuggested, event = event, onEvent = { onEvent(it) })
            }
        }
    }
}

@Composable
fun PlayersConfirmed(
    players: List<Player>,
    onEvent: (DetailEventEvent) -> Unit,
    event: Event
) {
    LazyColumn() {
        items(
            items = players,
            itemContent = { player ->
                FeatCardUser(
                    textNameUser = player.person.names + " " + player.person.lastname,
                    textPosition = player.position.description,
                    textLevel = player.level.description,
                    sportId = player.sport.sportGeneric.id,
                    content = {
                        Column(
                            modifier = Modifier.weight(.5f)
                        ) {
                            FeatButton(
                                textButton = "Expulsar",
                                colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                                onClick = { onEvent(DetailEventEvent.KickPlayer(player.id)) }
                            )
                        }
                    }
                )
            }
        )
    }
}

@Composable
fun PlayersApplied(
    players: List<PlayerApplyDetail>,
    onEvent: (DetailEventEvent) -> Unit,
    event: Event,
) {
    LazyColumn() {
        items(
            items = players,
            itemContent = { player ->
                FeatCardUser(
                    sportId = event.sport.sportGeneric.id,
                    textNameUser = player.names + " " + player.lastname,
                    textPosition = player.position,
                    textLevel = player.level,
                    content = {
                        if(player.origin == "Postulado"){
                            Column(
                                modifier = Modifier.weight(.5f)
                            ) {
                                FeatButton(
                                    textButton = "Rechazar",
                                    colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                                    onClick = { onEvent(DetailEventEvent.RejectPlayer(player.id)) }
                                )
                            }
                            Column(
                                modifier = Modifier.weight(.5f)
                            ) {
                                FeatButton(
                                    textButton = "Aceptar",
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                                    onClick = { onEvent(DetailEventEvent.AcceptPlayer(player.id)) }
                                )
                            }
                        }else if(player.origin == "Invitado"){
                            Column(
                                modifier = Modifier.weight(.5f)
                            ) {
                                FeatButton(
                                    textButton = "Cancelar invitacion",
                                    colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                                    onClick = { onEvent(DetailEventEvent.RejectPlayer(player.id)) }
                                )
                            }
                        }
                    }
                )
            }
        )
    }
}

@Composable
fun PlayersSuggested(
    players: List<Player>,
    onEvent: (DetailEventEvent) -> Unit,
    event: Event
) {
    LazyColumn() {
        items(
            items = players,
            itemContent = { player ->
                FeatCardUser(
                    textNameUser = player.person.names + " " + player.person.lastname,
                    textPosition = player.position.description,
                    textLevel = player.level.description,
                    content = {
                        FeatButton(
                            modifier = Modifier.padding(
                                top = 10.dp,
                                end = 40.dp,
                                start = 40.dp,
                                bottom = 10.dp
                            ),
                            textButton = "Invitar",
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            onClick = { onEvent(DetailEventEvent.InvitePlayer(player.id)) }
                        )
                    }
                )
            }
        )
    }
}