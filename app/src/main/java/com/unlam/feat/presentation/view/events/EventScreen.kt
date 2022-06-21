package com.unlam.feat.presentation.view.events

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.presentation.component.FeatAlertDialog
import com.unlam.feat.presentation.component.FeatCard
import com.unlam.feat.presentation.component.FeatCircularProgress
import com.unlam.feat.presentation.component.FeatHeader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Event(
    state: EventState,
    onEvent: (EventEvent) -> Unit,
    isRefreshing: Boolean,
    navigateToDetail: (Event) -> Unit,
    refreshData: () -> Unit
) {


    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = stringResource(R.string.title_error_events),
            descriptionContent = stringResource(R.string.error_load_events),
            onDismiss = {
                onEvent(EventEvent.DismissDialog)
            }
        )
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FeatHeader(text = stringResource(R.string.title_my_events))
        if (state.isLoading) {
            FeatCircularProgress()
        } else {
            if (state.events.isEmpty()) {
                Box(
                    Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center,

                ) {
                    Text(
                        text = "No ha creado ningun evento. \n" + "Presione el \"+\" y cree su primer evento. ",
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
                                        DateTimeFormatter.ofPattern(stringResource(R.string.format_date))
                                    )
                                    val context = LocalContext.current
                                    val location =
                                        LatLng(
                                            event.latitude.toDouble(),
                                            event.longitude.toDouble()
                                        )
                                    FeatCard(
                                        textNameEvent = event.name,
                                        textDay = "$date ${
                                            event.startTime.substring(
                                                0,
                                                5
                                            )
                                        } - ${event.endTime.substring(0, 5)}",
                                        textLocation = getAddress(location, context).getAddressLine(
                                            0
                                        ),
                                        textState = event.state.description,
                                        sport = event.sport.description,
                                        onClickCard = {
                                            navigateToDetail(event)
                                        },
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

private fun getAddress(latLng: LatLng, context: Context): Address {
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return list[0]
}
