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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.model.Person
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
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
    updatePerson: () -> Unit,
    updatePersonPreferences: () -> Unit,
    navigateToAddress: () -> Unit
) {

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
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = state.names,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Apellido",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = state.lastname,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Apodo",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = state.nickname,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Sexo",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = state.sex,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Fecha de nacimiento",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                            Text(
                                text =  state.birth_date.toString(),
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Black,
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
                                updatePersonPreferences()
                            }
                        )
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


                        FeatText(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Rango de edad",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Row(

                        ) {
                            FeatTextField(
                                textLabel = "Minima",
                                text = state.minAge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                keyboardType = KeyboardType.Number,
                                onValueChange = { age ->
                                    if (age.length <= 3 && ((age.toIntOrNull()) ?: 0) <= 150) {
                                        onValueChange(
                                            ProfileEvent.EnteredMinAge(
                                                age.filter { it.isDigit() })
                                        )
                                    }

                                }
                            )
                            FeatTextField(
                                textLabel = "Maxima",
                                text = state.maxAge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                keyboardType = KeyboardType.Number,
                                onValueChange = { age ->
                                    if (age.length <= 3 && ((age.toIntOrNull()) ?: 0) <= 150) {
                                        onValueChange(
                                            ProfileEvent.EnteredMaxAge(
                                                age.filter { it.isDigit() })
                                        )
                                    }
                                }
                            )
                        }

                        FeatText(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Rango de cercanía",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )

                        Row(

                        ) {

                            Column(

                            ) {

                                Row() {
                                    Slider(
                                        value = state.willingDistance.toFloat(),
                                        valueRange = 1f..50f,
                                        onValueChange = {
                                            onValueChange(
                                                ProfileEvent.EnteredWillingDistance(
                                                    it.roundToInt().toString()
                                                )
                                            )
                                        },
                                        steps = 0,
                                        colors = SliderDefaults.colors(
                                            thumbColor = MaterialTheme.colors.secondary,
                                            activeTrackColor = MaterialTheme.colors.secondary,
                                            inactiveTrackColor = Color.White.copy(alpha = 1.0f),
                                            activeTickColor = MaterialTheme.colors.secondary,
                                            inactiveTickColor = Color.White
                                        )
                                    )
                                }
                                Row() {
                                    Text(state.willingDistance + " Km.")

                                }

                            }


                        }
                        Row(

                        ) {

                            val isChecker = remember { mutableStateOf(false) }
                            FeatLabelledCheckbox(
                                checked = isChecker.value,
                                onCheckedChange = {
                                    isChecker.value = it
                                    ProfileEvent.EnteredNotifications(it)
                                },
                                label = "Recibir notificaciones"
                            )
                        }
                            FeatButtonRounded(
                                modifier = Modifier
                                    .size(45.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.End),
                                drawable = R.drawable.add,
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                                colorFilter = ColorFilter.tint(Color.White),
                                onClick = {
                                    updatePersonPreferences()
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

                    state.addresses?.forEachIndexed { index, address ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = address.street,
                                //style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = " " + address.number,
                                //style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = ", " + address.town,
                                //style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    FeatButtonRounded(
                        modifier = Modifier
                            .size(45.dp)
                            .fillMaxWidth()
                            .align(Alignment.End),
                        drawable = R.drawable.add,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        onClick = {navigateToAddress()},
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
                            //sportDescription = player.sport.description,
                            idSport = player.sport.id,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            onClickCard = { refreshData() })
                        /*
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                            Text(
                                text = player.sport.description,
                                //style = MaterialTheme.typography.h6
                            )
                    }
                    */
                    }
                }

            }
        }
    }
    }

}