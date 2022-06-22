package com.unlam.feat.presentation.view.home.detail_event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.view.invitation.detail_invitation.DetailInvitationEvent

@Composable
fun DetailEventHome(
    state: DetailEventHomeState,
    onEvent: (DetailEventHomeEvent) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")
    val event = state.event
    val playersConfirmed = state.players


    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = "Ocurrio un error",
            descriptionContent = "No se pudo procesar la solicitud, por favor, vuelva a intentarlo",
            onDismiss = {
                onEvent(DetailEventHomeEvent.DismissDialog)
            }
        )
    }

    if (state.loading) {
        FeatCircularProgress()
    } else {
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
                0 -> FeatCardEventDetail(event!!)
                1 -> FeatCardListPLayer(playersConfirmed!!)
            }
        }
    }
}

