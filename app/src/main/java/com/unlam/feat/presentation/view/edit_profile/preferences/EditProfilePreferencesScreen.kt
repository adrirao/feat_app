package com.unlam.feat.presentation.view.edit_profile.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import com.unlam.feat.R
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text
import com.unlam.feat.presentation.view.edit_profile.ProfileEvent
import com.unlam.feat.presentation.view.edit_profile.ProfileState
import kotlin.math.roundToInt


@Composable
fun Preferences(
    navController: NavHostController,
    state: EditProfilePreferencesState,
    onValueChange: (EditProfilePreferencesEvent) -> Unit,
    updatePersonPreferences: () -> Unit,
    navigateToProfile: () -> Unit
) {

    if (state.isSuccessSubmitData) {
        LaunchedEffect(true) {
            navigateToProfile()
        }
    }

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
                                            EditProfilePreferencesEvent.EnteredMinAge(
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
                                            EditProfilePreferencesEvent.EnteredMaxAge(
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
                                                EditProfilePreferencesEvent.EnteredWillingDistance(
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
                                    EditProfilePreferencesEvent.EnteredNotifications(it)
                                },
                                label = "Recibir notificaciones"
                            )
                        }
                        FeatButton(
                            modifier = Modifier
                                .padding(10.dp)
                                .height(60.dp),
                            textButton = "Modificar preferencias",
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            colorText = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                            onClick = {
                                updatePersonPreferences()
                            }
                        )
                    }

                }
            }
        }
    }

}