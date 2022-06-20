package com.unlam.feat.presentation.view.events.detail_event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.view.invitation.detail_invitation.DetailInvitationEvent

@Composable
fun DetailEventScreen(
    state: DetailEventState,
    onEvent: (DetailEventEvent) -> Unit
) {

    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")
    val event = state.event
    val playerSuggested = state.playersSuggested
    val playersApplied = state.playersApplied
    val playersConfirmed = state.playersConfirmed


//    if (state.error.isNotBlank()) {
//        FeatAlertDialog(
//            title = "Ocurrio un error",
//            descriptionContent = "No se pudo procesar la solicitud, por favor, vuelva a intentarlo",
//            onDismiss = {
//                onEvent(DetailInvitationEvent.DismissDialog)
//            }
//        )
//    }
//    if (state.success) {
//        FeatAlertDialog(
//            title = state.successTitle,
//            descriptionContent = state.successDescription,
//            onDismiss = {
//                navigateToInvitation()
//            }
//        )
//    }


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
                        onClick = { onEvent(DetailEventEvent.DismissDialog) },
                    )

                    FeatButton(
                        textButton = "Confirmar",
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        onClick = { onEvent(DetailEventEvent.DismissDialog) },
                    )
                }
            }
            1 -> ParticipantsDetail(
                playerSuggested!!,
                playersApplied!!,
                playersConfirmed!!,
                onEvent
            )
        }
    }

}


@Composable
fun ParticipantsDetail(
    playerSuggested: List<Player>,
    playerApplied: List<Player>,
    playerConfirmed: List<Player>,
    onEvent: (DetailEventEvent) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Confirmados", "Postulados" ,"Sugeridos")

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
        when (tabIndex) {
            0 -> FeatCardListPLayer(playerConfirmed){
                Column(
                    modifier = Modifier.weight(.5f)
                ) {
                    FeatButton(
                        textButton = "Expulsar",
                        colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                        onClick = { onEvent(DetailEventEvent.DismissDialog) }
                    )
                }
            }
            1 -> FeatCardListPLayer(playerApplied){
                Column(
                    modifier = Modifier.weight(.5f)
                ) {
                    FeatButton(
                        textButton = "Rechazar",
                        colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                        onClick = { onEvent(DetailEventEvent.DismissDialog) }
                    )
                }
                Column(
                    modifier = Modifier.weight(.5f)
                ) {
                    FeatButton(
                        textButton = "Aceptar",
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        onClick = { onEvent(DetailEventEvent.DismissDialog) }
                    )
                }
            }
            2 -> FeatCardListPLayer(playerSuggested){
                FeatButton(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        end = 40.dp,
                        start = 40.dp,
                        bottom = 10.dp
                    ),
                    textButton = "Invitar",
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = { onEvent(DetailEventEvent.DismissDialog) }
                )
            }
        }
    }
}

//
//@Composable
//fun PlayersAppliedAndSuggested(
//    playerApplied: List<Player>,
//    playerSuggested: List<Player>,
//    onEvent: (DetailEventEvent) -> Unit
//) {
//    Box() {
//
//        Column() {
//
//
//            Row(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) {
//
//
//                FeatCardListPLayer(players = playerApplied) {
//                    Column(
//                        modifier = Modifier.weight(.5f)
//                    ) {
//                        FeatButton(
//                            textButton = "Rechazar",
//                            colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
//                            onClick = { onEvent(DetailEventEvent.DismissDialog) }
//                        )
//                    }
//                    Column(
//                        modifier = Modifier.weight(.5f)
//                    ) {
//                        FeatButton(
//                            textButton = "Aceptar",
//                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
//                            onClick = { onEvent(DetailEventEvent.DismissDialog) }
//                        )
//                    }
//                }
//            }
//            Divider(
//                modifier = Modifier.padding(vertical = 0.dp),
//                color = MaterialTheme.colors.primary
//            )
//            Row(modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth()) {
//                FeatCardListPLayer(players = playerSuggested) {
//
//                    FeatButton(
//                        modifier = Modifier.padding(
//                            top = 10.dp,
//                            end = 40.dp,
//                            start = 40.dp,
//                            bottom = 10.dp
//                        ),
//                        textButton = "Invitar",
//                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
//                        onClick = { onEvent(DetailEventEvent.DismissDialog) }
//                    )
//
//                }
//
//
//            }
//        }
//    }
//
//}