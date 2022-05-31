package com.unlam.feat.presentation.view.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.unlam.feat.presentation.component.FeatCard
import com.unlam.feat.presentation.component.FeatCircularProgress
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Event(
    state: EventState,
    isRefreshing: Boolean,
    refreshData: () -> Unit
) {
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
                        )
                    }
                )
            }
        }
    }
    if (state.isLoading) {
        FeatCircularProgress()
    }
}
