package com.unlam.feat.presentation.view.home

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.presentation.component.FeatAlertDialog
import com.unlam.feat.presentation.component.FeatCard
import com.unlam.feat.presentation.component.FeatCircularProgress
import com.unlam.feat.presentation.component.FeatHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    isRefreshing: Boolean,
    navigateToDetail: (Int) -> Unit,
    refreshData: () -> Unit,
    navigateToDetailApply: (Int) -> Unit
) {
    val pagerState = rememberPagerState()

    if (state.eventOfTheWeek.isNotEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2000)
                tween<Float>(600)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                )
            }
        }
    }
    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = stringResource(R.string.error_home),
            descriptionContent = stringResource(R.string.error_load_events),
            onDismiss = {
                onEvent(HomeEvent.DismissDialog)
            }
        )
    }


    Column {
        FeatHeader(text = stringResource(R.string.text_home), dividerOn = false)
        if (state.isLoading) {
            FeatCircularProgress()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = refreshData
                ) {
                    Column {
                        Column {
                            Row(
                                Modifier
                                    .background(MaterialTheme.colors.primaryVariant)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Eventos de la semana:",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 20.dp,
                                            vertical = 10.dp
                                        )
                                        .background(MaterialTheme.colors.primaryVariant),
                                    fontSize = 18.sp,
                                )
                            }
                            Divider(
                                thickness = 3.dp,
                                modifier = Modifier.padding(vertical = 0.dp),
                                color = MaterialTheme.colors.secondary
                            )

                            if (state.eventOfTheWeek.isEmpty()) {
                                Box(
                                    Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(vertical = 70.dp),
                                        text = "No hay ningun evento organizado esta semana.",
                                        style = TextStyle(Color.Gray, fontSize = 18.sp)
                                    )
                                }

                            } else {
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier
                                        .padding(0.dp, 5.dp, 0.dp, 20.dp),
                                    count = state.eventOfTheWeek.size
                                ) { page ->
                                    val event = state.eventOfTheWeek[page]

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
                                            navigateToDetailApply(event.id)
                                        },
                                        )

                                }
                            }
                        }

                        Column {

                            Row(
                                Modifier
                                    .background(MaterialTheme.colors.primaryVariant)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Eventos en los que participo:",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 20.dp,
                                            vertical = 10.dp
                                        )
                                        .background(MaterialTheme.colors.primary),
                                    fontSize = 18.sp
                                )
                            }
                            Divider(
                                thickness = 3.dp,
                                modifier = Modifier.padding(vertical = 0.dp),
                                color = MaterialTheme.colors.secondary
                            )
                            if (state.eventConfirmedOrApplied.isEmpty()) {
                                Box(
                                    Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(modifier = Modifier.padding(vertical = 70.dp),
                                        text = "Aun no esta confirmado, ni postulado en algun evento",
                                        style = TextStyle(Color.Gray, fontSize = 18.sp)
                                    )
                                }

                            } else {
                                LazyColumn(
                                    contentPadding = PaddingValues(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                                ) {
                                    items(
                                        items = state.eventConfirmedOrApplied,
                                        itemContent = { event ->
                                            val date =
                                                LocalDate.parse(event.date.substring(0, 10)).format(
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
                                                textLocation = getAddress(
                                                    location,
                                                    context
                                                ).getAddressLine(
                                                    0
                                                ),
                                                textStatePlayer = event.origen,
                                                sport = event.sportDesc,
                                                onClickCard = {
                                                    navigateToDetail(event.id)
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
    }
}

private fun getAddress(latLng: LatLng, context: Context): Address {
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return list[0]
}