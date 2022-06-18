package com.unlam.feat.presentation.view.config_profile.additional_information


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
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



    if (state.isLoading) {
        FeatCircularProgress()
    }

    SetMessagesAditionalInformation(
        state,
        navController,
        onValueChange
    )

    ConfigProfileAdditionalInformationContent(state, navigateToConfigSport = {
        navController.popBackStack()
        navController.navigate(Screen.ConfigSport.route)
    }, onValueChange)
}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ConfigProfileAdditionalInformationContent(
    state: ConfigProfileAdditionalInformationState,
    navigateToConfigSport: () -> Unit,
    onValueChange: (ConfigProfileAdditionalInformationEvent) -> Unit
) {
    if (state.isSuccessSubmitData) {
        LaunchedEffect(true) {
            navigateToConfigSport()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .size(200.dp, 60.dp)
                    .padding(bottom = 10.dp),
                painter = painterResource(R.drawable.ic_isologotype_2),
                contentDescription = stringResource(R.string.feat_logo)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 0.15f, fill = false)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            FeatText(
                text = "Ingresa los siguientes datos adicionales. 4/5",
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
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 0.8f, fill = true)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.SpaceBetween,

                ) {


                FeatText(
                    modifier = Modifier.padding(top = 10.dp),
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
                    modifier = Modifier.padding(top = 10.dp),
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
                                valueRange = 1f..50f,
                                onValueChange = {
                                    onValueChange(
                                        ConfigProfileAdditionalInformationEvent.EnteredWillingDistance(
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



                FeatText(
                    modifier = Modifier.padding(top = 10.dp),
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

            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .weight(0.1f, false),
                verticalAlignment = Alignment.Bottom
            ) {
                FeatButtonRounded(
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .size(60.dp)
                    ,
                    drawable = R.drawable.arrow_next,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = {
                      onValueChange(ConfigProfileAdditionalInformationEvent.SubmitData)
                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }

        }


    }
}


@Composable
private fun SetMessagesAditionalInformation(
    state: ConfigProfileAdditionalInformationState,
    navController: NavController,
    onValueChange: (ConfigProfileAdditionalInformationEvent) -> Unit
) {
    if (state.error != null) {
        FeatAlertDialog(
            title = "Error de conexión",
            descriptionContent = "No se ha podido conectar con el servidor, vuelva a intentarlo.",
            onDismiss = {
                onValueChange(ConfigProfileAdditionalInformationEvent.DismissDialog)
                onValueChange(ConfigProfileAdditionalInformationEvent.SingOutUser)
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )
    }

        if (state.ageError != null) {
            var title: String = "Error en el rango de edad ingresado"
            var description: String
            when (state.ageError) {
                ConfigProfileAdditionalInformationState.RangeAgeError.MinAgeEmpty -> {
                    description = "La edad minima no puede estar vacia."
                }
                ConfigProfileAdditionalInformationState.RangeAgeError.MaxAgeEmpty -> {
                    description = "La edad maxima no puede estar vacia."
                }
                ConfigProfileAdditionalInformationState.RangeAgeError.FieldEmpty -> {
                    description = "Los campos edad maxima y minima no pueden estar vacios."
                }
                ConfigProfileAdditionalInformationState.RangeAgeError.IsInvalidRange -> {
                    description = "La edad minima no puede superar a la edad maxima."
                }

            }
            FeatAlertDialog(
                title = title,
                descriptionContent = description,
                onDismiss = {
                    onValueChange(ConfigProfileAdditionalInformationEvent.DismissDialog)
                }
            )
        }
}
