package com.unlam.feat.presentation.view.edit_profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.model.ListSportId
import com.unlam.feat.model.Person
import com.unlam.feat.model.Player
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text
import com.unlam.feat.presentation.view.config_profile.additional_information.ConfigProfileAdditionalInformationEvent
import com.unlam.feat.presentation.view.events.add_event.AddEventEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun Profile(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    onValueChange: (ProfileEvent) -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    navigateToAddress: () -> Unit,
    navigateToPersonalInformation: () -> Unit,
    navigateToPlayerInformation: (String) -> Unit,
    navigateToPreferencies: () -> Unit,
    onClick : () -> Unit
) {

    val date = state.person?.birthDate.toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FeatHeader(text = stringResource(R.string.title_my_profile))
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            {

                Card(
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    elevation = 6.dp,
                    backgroundColor = MaterialTheme.colors.card,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    )
                    {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis datos personales",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Nombres",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            state.person?.let {
                                Text(
                                    text = it.names,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Apellido",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            state.person?.let {
                                Text(
                                    text = it.lastname,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Apodo",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            state.person?.let {
                                Text(
                                    text = it.nickname,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Sexo",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            state.person?.let {
                                Text(
                                    text = it.sex,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Fecha de nacimiento",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = date,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        FeatButtonRounded(
                            modifier = Modifier
                                .size(45.dp)
                                .fillMaxWidth()
                                .align(Alignment.End),
                            drawable = R.drawable.edit,
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            colorFilter = ColorFilter.tint(Color.White),
                            onClick = {
                                navigateToPersonalInformation()
                            }
                        )
                    }

                }
                // Availabilities
                Card(
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    elevation = 6.dp,
                    backgroundColor = MaterialTheme.colors.card,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    )
                    {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mi disponibilidad",
                                style = MaterialTheme.typography.h5
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            state.availabilities?.forEachIndexed { index, availability ->
                                Text(
                                    text = availability.day.description,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {

                                    Text(
                                        text = availability.startTime,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        text = availability.endTime,
                                        //modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                }

                            }
                        }
                    }

                }

                // Preferencias
                Card(
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    elevation = 6.dp,
                    backgroundColor = MaterialTheme.colors.card,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    )
                    {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis preferencias",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Rango de edad",
                                style = MaterialTheme.typography.h6
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Mínima",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = state.person?.minAge.toString(),
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Máxima",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = state.person?.maxAge.toString(),
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Rango de cercanía",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = state.person?.willingDistance.toString() + " Km",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        FeatButtonRounded(
                            modifier = Modifier
                                .size(45.dp)
                                .fillMaxWidth()
                                .align(Alignment.End),
                            drawable = R.drawable.edit,
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            colorFilter = ColorFilter.tint(Color.White),
                            onClick = {
                                navigateToPreferencies()
                            }
                        )

                    }

                }

                // Direcciones
                Card(
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    elevation = 6.dp,
                    backgroundColor = MaterialTheme.colors.card,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    )
                    {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis direcciones",
                                style = MaterialTheme.typography.h5
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            state.addresses?.forEachIndexed { index, address ->
                                Text(
                                    text = address.alias,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                    //style = MaterialTheme.typography.h6
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {

                                    Text(
                                        text = address.street,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        text = " " + address.number,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        text = ", " + address.town,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        text = address.street,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        text = " " + address.number,
                                        //modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                    Text(
                                        text = ", " + address.town,
                                        //modifier = Modifier.padding(horizontal = 10.dp),
                                        color = MaterialTheme.colors.text,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                        //style = MaterialTheme.typography.h6
                                    )
                                }
                            }
                        }

                        FeatButtonRounded(
                            modifier = Modifier
                                .size(45.dp)
                                .fillMaxWidth()
                                .align(Alignment.End),
                            drawable = R.drawable.add,
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            onClick = { navigateToAddress() },
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                }

                // Mis Deportes
                Card(
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    elevation = 6.dp,
                    backgroundColor = MaterialTheme.colors.card,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    )
                    {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis intereses deportivos",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        state.players?.forEach { player ->

                            FeatSportCard(
                                sport = player.sport.description,
                                idSport = player.sport.id,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                onClickCard = {
                                    val moshi =
                                        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                                    val jsonAdapter = moshi.adapter(Player::class.java).lenient()
                                    val playerJson = jsonAdapter.toJson(player)

                                    navigateToPlayerInformation(playerJson)
                                })

                        }
                    }

                }
                FeatButton(
                    textButton = "Cerrar Sesion",
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = { onClick() }
                )
            }

        }
    }

}