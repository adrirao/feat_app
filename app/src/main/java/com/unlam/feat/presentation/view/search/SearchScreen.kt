package com.unlam.feat.presentation.view.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.unlam.feat.model.Event
import com.unlam.feat.presentation.component.FeatAlertDialog
import com.unlam.feat.presentation.component.FeatCard
import com.unlam.feat.presentation.component.FeatCircularProgress
import com.unlam.feat.presentation.component.FeatHeader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Search(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onClickCard: (Event) -> Unit
) {


    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = "Error Eventos",
            descriptionContent = "No se pudo cargar los eventos.",
            onDismiss = {
                onEvent(SearchEvent.DismissDialog)
            }
        )
    }
    Column() {
        FeatHeader(text = "Buscar Evento")
        if (state.isLoading) {
            FeatCircularProgress()
        } else {
            if (state.events.isEmpty()) {
                Box(
                    Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay eventos que coincidan con su perfil para mostrar",
                        style = TextStyle(Color.Gray, fontSize = 18.sp)
                    )
                }

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = refreshData
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(
                                items = state.events,
                                itemContent = { event ->
                                    val date = LocalDate.parse(event.date.substring(0, 10)).format(
                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                    )
                                    FeatCard(
                                        textNameEvent = event.name,
                                        textDay = "$date ${
                                            event.startTime.substring(
                                                0,
                                                5
                                            )
                                        } - ${event.endTime.substring(0, 5)}",
                                        textState = event.state.description,
                                        sport = event.sport.description,
                                        onClickCard = {
                                            onClickCard(event)
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}