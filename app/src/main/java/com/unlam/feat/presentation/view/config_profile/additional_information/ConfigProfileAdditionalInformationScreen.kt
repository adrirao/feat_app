package com.unlam.feat.presentation.view.config_profile.additional_information


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import kotlin.math.roundToInt


@Composable
fun ConfigProfileAdditionalInformationScreen(
    navController: NavHostController,
    state: ConfigProfileAdditionalInformationState,
    onValueChange: (ConfigProfileAdditionalInformationEvent) -> Unit
) {
    ConfigProfileAdditionalInformationContent(state, navigateToConfigAdditionalInformation = {
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }, onValueChange)
}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ConfigProfileAdditionalInformationContent(
    state: ConfigProfileAdditionalInformationState,
    navigateToConfigAdditionalInformation: () -> Unit,
    onValueChange: (ConfigProfileAdditionalInformationEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(R.drawable.ic_isologotype_2),
                contentDescription = stringResource(R.string.feat_logo)
            )
            FeatText(
                text = "Ingresa los siguientes datos adicionales.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                verticalArrangement = Arrangement.Center,

                ) {


                FeatText(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "Ingresa el rango de edad que te gustaria encontrar en los eventos",
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
                                    ConfigProfileAdditionalInformationEvent.EnteredMinAge(
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
                                    ConfigProfileAdditionalInformationEvent.EnteredMaxAge(
                                        age.filter { it.isDigit() })
                                )
                            }
                        }
                    )
                }

                FeatText(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "¿Cuanto estas dispuesto a trasladarte?",
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
                                valueRange = 0f..20000f,
                                onValueChange = {
                                    onValueChange(ConfigProfileAdditionalInformationEvent.EnteredWillingDistance(it.roundToInt().toString()))
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
                            Text(state.willingDistance  + " Km.")

                        }

                    }



                }



                FeatText(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "¿Te gustaria recibir notificaciones de Eventos?",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Row(

                ) {

                    val isChecker = remember { mutableStateOf(false) }
                    FeatLabelledCheckbox(
                        checked = isChecker.value,
                        onCheckedChange = {
                            isChecker.value = it
                            ConfigProfileAdditionalInformationEvent.EnteredNotifications(it)
                        },
                        label = "Recibir notificaciones"
                    )
                }



                FeatButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(60.dp),
                    textButton = "Siguiente",
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    colorText = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    onClick = {
                        navigateToConfigAdditionalInformation()
                        //persistir en la base
                    }
                )
            }

        }
    }
}


