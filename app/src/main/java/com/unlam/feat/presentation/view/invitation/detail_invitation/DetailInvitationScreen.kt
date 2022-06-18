package com.unlam.feat.presentation.view.invitation.detail_invitation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun DetailInvitationScreen(
    state: DetailInvitationState,
    onEvent: (DetailInvitationEvent) -> Unit,
    navigateToInvitation: () -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")
    val event = state.event
    val playersConfirmed = state.playersConfirmed


    if (state.error.isNotBlank()) {
        FeatAlertDialog(
            title = "Ocurrio un error",
            descriptionContent = "No se pudo procesar la solicitud, por favor, vuelva a intentarlo",
            onDismiss = {
                onEvent(DetailInvitationEvent.DismissDialog)
            }
        )
    }
    if (state.success) {
        FeatAlertDialog(
            title = state.successTitle,
            descriptionContent = state.successDescription,
            onDismiss = {
                navigateToInvitation()
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
                0 -> FeatCardEventDetail(event!!, onEvent)
                1 -> FeatCardListPLayer(playersConfirmed!!)
            }
        }
    }
}






