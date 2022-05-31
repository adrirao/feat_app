package com.unlam.feat.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.unlam.feat.presentation.component.FeatCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Login(
    state: LoginState,
    isRefreshing: Boolean,
    refreshData: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

    }
}
