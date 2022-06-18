package com.unlam.feat.presentation.component

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.unlam.feat.R
import com.unlam.feat.model.Event
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text
import com.unlam.feat.presentation.view.invitation.detail_invitation.DetailInvitationEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@Composable
fun FeatCardEventDetail(
    event: Event,
    onClick: (DetailInvitationEvent) -> Unit
) {

    val context = LocalContext.current
    val location = LatLng(event.latitude.toDouble(), event.longitude.toDouble())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .clip(Shapes.large)
                .fillMaxWidth(0.9f)
                .background(MaterialTheme.colors.card)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (event.sport.description.contains("Futbol")) {
                        Image(
                            painter = painterResource(id = R.drawable.soccer),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(70.dp)
                        )
                    } else if (event.sport.description.contains("Padel")) {
                        Image(
                            painter = painterResource(id = R.drawable.padel),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(70.dp)
                        )
                    } else if (event.sport.description.contains("Tenis")) {
                        Image(
                            painter = painterResource(id = R.drawable.tennis),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(70.dp)
                        )
                    } else if (event.sport.description.contains("Basquet ")) {
                        Image(
                            painter = painterResource(id = R.drawable.basketball),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(70.dp)
                        )
                    } else if (event.sport.description.contains("Actividad Recreativa")) {
                        Image(
                            painter = painterResource(id = R.drawable.recreational_event),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = event.name,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = MaterialTheme.colors.secondary,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = event.state.description,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = MaterialTheme.colors.secondaryVariant,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.watch),
                        contentDescription = "Day",
                        contentScale = ContentScale.Fit,
                    )
                    val date = LocalDate.parse(event.date.substring(0, 10)).format(
                        DateTimeFormatter.ofPattern(stringResource(R.string.format_date))
                    )
                    Text(
                        text = "$date ${
                            event.startTime.substring(
                                0,
                                5
                            )
                        } - ${event.endTime.substring(0, 5)}",
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = MaterialTheme.colors.text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.logotipo),
                        contentDescription = "image",
                        contentScale = ContentScale.Fit,
                    )

                    Text(
                        text = getAddress(location, context).getAddressLine(0),
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = MaterialTheme.colors.text,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    FeatButtonRounded(
                        modifier = Modifier.padding(0.dp),
                        drawable = R.drawable.navigate,
                        colorFilter = null,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                        modifierImage = Modifier.size(30.dp),
                        onClick = {
                            howToGet(location,context)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = event.description,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = MaterialTheme.colors.text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(

            ) {
                FeatButtonRounded(
                    modifier = Modifier
                        .size(60.dp)
                        .fillMaxWidth(),
                    drawable = R.drawable.cancel,
                    colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                    onClick = { onClick(DetailInvitationEvent.CancelInvitation)},
                    colorFilter = ColorFilter.tint(Color.White)
                )

            }
            Column(
            ) {
                FeatButtonRounded(
                    modifier = Modifier
                        .size(60.dp)
                        .fillMaxWidth(),
                    drawable = R.drawable.check,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = {onClick(DetailInvitationEvent.ConfirmInvitation)},
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

        }

    }
}

private fun getAddress(latLng: LatLng, context: Context): Address {
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return list[0]
}

private fun howToGet(latLong: LatLng, context: Context) {
    val intentUriNavigation = Uri.parse(
        "google.navigation:q=${latLong.latitude},${latLong.longitude}"
    )
    try {
        Intent(Intent.ACTION_VIEW, intentUriNavigation).apply {
            setPackage("com.google.android.apps.maps")
            ContextCompat.startActivity(context, this, null)
        }
    } catch (ignored: ActivityNotFoundException) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=com.google.android.apps.maps")
        ).apply {
            ContextCompat.startActivity(context, this, null)
        }

    }
}