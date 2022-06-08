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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.presentation.component.FeatButton
import com.unlam.feat.presentation.component.FeatHeader
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card

@Composable
fun DetailEventScreen(
    state: DetailEventState
) {

    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")
    val event = state.event
    val playerSuggested = state.playersSuggested
    val playersApplied = state.playersApplied
    val playersConfirmed = state.playersConfirmed

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
            0 -> DetailEvent(event!!)
            1 -> ParticipantsDetail(playerSuggested!!,playersApplied!!,playersConfirmed!!)
        }
    }

}

@Composable
fun DetailEvent(
    event: Event
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
                    .fillMaxWidth(0.5F)
                    .padding(10.dp)
                    .height(60.dp),
                textButton = "Rechazar"
            )
            FeatButton(
                textButton = "Confirmar"
            )
        }
    }
}

@Composable
fun ParticipantsDetail(
    playerSuggested: List<Player>,
    playerApplied : List<Player>,
    playerConfirmed: List<Player>
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Jugadores", "Sugeridos y Aplicados")

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
            0 -> PlayersConfirmed(playerConfirmed)
            1 -> PlayersAppliedAndSuggested(playerApplied,playerSuggested)
        }
    }
}

@Composable
fun PlayersConfirmed(
    playerConfirmed: List<Player>
){
    LazyColumn(){
        items(
            items = playerConfirmed,
            itemContent = { player ->
                Text(text = player.person.lastname)
            }
        )
    }
}

@Composable
fun PlayersAppliedAndSuggested(
    playerApplied: List<Player>,
    playerSuggested: List<Player>
){
    LazyColumn(){
        items(
            items = playerApplied,
            itemContent = { player ->
                Text(text = player.person.lastname)
            }
        )
    }
    LazyColumn(){
        items(
            items = playerSuggested,
            itemContent = { player ->
                Text(text = player.person.lastname)
            }
        )
    }
}