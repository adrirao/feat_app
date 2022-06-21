package com.unlam.feat.presentation.view.search.event_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card


@Composable
fun SearchEventDetailScreen(
    state: SearchEventDetailState,
    onEvent: (SearchEventDetailEvent) -> Unit,
    navigateToSearch: () -> Unit

) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")
    val event = state.event
    val playersConfirmed = state.playersConfirmed

    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = "Ocurrio un error",
            descriptionContent = "No se pudo procesar la solicitud",
            onDismiss = {
                onEvent(SearchEventDetailEvent.DismissDialog)
            }
        )
    }
    if (state.success) {
        FeatAlertDialog(
            title = "Enhorabuena",
            descriptionContent = "Solicitud Enviada Correctamente!!",
            onDismiss = {
                navigateToSearch()
            }
        )
    }
    if (state.loading) {
        FeatCircularProgress()
    }else{

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
                            textButton = "Quiero Participar",
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            onClick = { onEvent(SearchEventDetailEvent.ApplyEvent) }
                        )
                    }
                }
                1 -> ParticipantsDetail(
                    playersConfirmed!!,
                    onEvent
                )
            }
        }

    }

}

@Composable
fun SearchEventDetail(
    event: Event,
    onEvent: (SearchEventDetailEvent) ->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .clip(Shapes.large)
                .fillMaxWidth(0.7f)
                .background(MaterialTheme.colors.card)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Column {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_isologotype_2),
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(painter = painterResource(id = R.drawable.watch), contentDescription = "")
                    Text(text = event.startTime)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.logotipo),
                        contentDescription = "image"
                    )
                    Text(text = event.latitude)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = event.description
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ) {

            FeatButton(
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                textButton = "Postularme a Evento",
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                colorText = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                onClick = {
                    onEvent(SearchEventDetailEvent.ApplyEvent)
                }
            )
        }
    }
}

@Composable
fun ParticipantsDetail(
    playerConfirmed: List<Player>,
    onEvent: (SearchEventDetailEvent) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Confirmados")

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
            0 -> FeatCardListPLayer(playerConfirmed)
        }
    }
}