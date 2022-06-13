package com.unlam.feat.presentation.view.config_profile.availability

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*



@Composable
fun ConfigProfileAvailabilityScreen(
    navController: NavHostController,
    state: ConfigProfileAvailabilityState,
    onValueChange: (ConfigProfileAvailabilityEvent) -> Unit
) {

SetMessagesAvailabilities(
        state = state,
        navController = navController,
        onValueChange = onValueChange
    )

    ConfigProfileAvailabilityContent(state, navigateToConfigProfileAdditionalInformation = {
        navController.popBackStack()
        navController.navigate(Screen.ConfigProfileAdditionalInformation.route)
    }, onValueChange)
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ConfigProfileAvailabilityContent(
    state: ConfigProfileAvailabilityState,
    navigateToConfigProfileAdditionalInformation: () -> Unit,
    onValueChange: (ConfigProfileAvailabilityEvent) -> Unit
) {

    if (state.isSuccessSubmitData) {
        LaunchedEffect(true) {
            navigateToConfigProfileAdditionalInformation()
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 0.15f, fill = false)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(200.dp, 60.dp)
                        .padding(bottom = 10.dp),
                    painter = painterResource(R.drawable.ic_isologotype_2),
                    contentDescription = stringResource(R.string.feat_logo)
                )
                FeatText(
                    text = "Ingresa tu disponibilidad horaria. 3/5",
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

                ) {


                FeatAvailabilityCheckBoxPickerTime(
                    state.sundayIsChecked,
                    { onValueChange(ConfigProfileAvailabilityEvent.SundayIsChecked(it))},
                    "Domingo",
                    state.startTimeSunday,
                    state.endTimeSunday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime1(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime1(
                                it
                            )
                        )
                    },
                )
                FeatAvailabilityCheckBoxPickerTime(
                    state.mondayIsChecked,
                    { onValueChange(ConfigProfileAvailabilityEvent.MondayIsChecked(it)) },
                    "Lunes",
                    state.startTimeMonday,
                    state.endTimeMonday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime2(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime2(
                                it
                            )
                        )
                    },
                )

                FeatAvailabilityCheckBoxPickerTime(
                    state.tuesdayIsChecked,
                    {onValueChange(ConfigProfileAvailabilityEvent.TuesdayIsChecked(it)) },
                    "Martes",
                    state.startTimeTuesday,
                    state.endTimeTuesday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime3(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime3(
                                it
                            )
                        )
                    },
                )
                FeatAvailabilityCheckBoxPickerTime(
                    state.wednesdayIsChecked,
                    {onValueChange(ConfigProfileAvailabilityEvent.WednesdayIsChecked(it)) },
                    "Miercoles",
                    state.startTimeWednesday,
                    state.endTimeWednesday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime4(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime4(
                                it
                            )
                        )
                    },
                )
                FeatAvailabilityCheckBoxPickerTime(
                    state.thursdayIsChecked,
                    { onValueChange(ConfigProfileAvailabilityEvent.ThursdayIsChecked(it)) },
                    "Jueves",
                    state.startTimeThursday,
                    state.endTimeThursday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime5(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime5(
                                it
                            )
                        )
                    },
                )
                FeatAvailabilityCheckBoxPickerTime(
                    state.fridayIsChecked,
                    {onValueChange(ConfigProfileAvailabilityEvent.FridayIsChecked(it)) },
                    "Viernes",
                    state.startTimeFriday,
                    state.endTimeFriday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime6(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime6(
                                it
                            )
                        )
                    },
                )
                FeatAvailabilityCheckBoxPickerTime(
                    state.saturdayIsChecked,
                    { onValueChange(ConfigProfileAvailabilityEvent.SaturdayIsChecked(it)) },
                    "Sabado",
                    state.startTimeSaturday,
                    state.endTimeSaturday,
                    onValueChangeStartTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredStartTime7(
                                it
                            )
                        )
                    },
                    onValueChangeEndTime = {
                        onValueChange(
                            ConfigProfileAvailabilityEvent.EnteredEndTime7(
                                it
                            )
                        )
                    },
                )
            }
            Row(
                modifier = Modifier
                    .align(End)
                    .weight(0.1f, false),
                verticalAlignment = Alignment.Bottom

            ) {
                FeatButtonRounded(
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .size(60.dp),
                    drawable = R.drawable.arrow_next,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = {
                        onValueChange(ConfigProfileAvailabilityEvent.SubmitData)
                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }

        }

    }

}

@Composable
private fun SetMessagesAvailabilities(
    state: ConfigProfileAvailabilityState,
    navController: NavController,
    onValueChange: (ConfigProfileAvailabilityEvent) -> Unit
) {
    if (state.error != null) {
        FeatAlertDialog(
            title = "Error de conexión",
            descriptionContent = "No se ha podido conectar con el servidor, vuelva a intentarlo.",
            onDismiss = {
                onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                onValueChange(ConfigProfileAvailabilityEvent.SingOutUser)
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )
    }

    if (state.allDayEmpty) {
        FeatAlertDialog(
            title = "No se ha agregado ningun rango horario",
            descriptionContent = "Por favor, verificar que se haya ingresado un rango horario.",
            onDismiss = {
                onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
            }
        )
    }

    var countFieldEmptys: List<String> = state.fieldError.split(',')


    if (state.fieldError != "" && countFieldEmptys.size > 2) {
        FeatAlertDialog(
            title = "Datos ingresados incorrectos",
            descriptionContent = "Por favor, verifica que los siguientes campos  ${state.fieldError}",
            onDismiss = {
                onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
            }
        )
    } else {
        if (state.sundayError != null) {
            var title: String = "Error en el horario del Domingo"
            var description: String
            when(state.sundayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent =description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }
        if (state.mondayError != null) {
            var title: String = "Error en el horario del Lunes"
            var description: String
            when(state.mondayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent = description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }
        if (state.tuesdayError != null) {
            var title: String = "Error en el horario del Martes"
            var description: String
            when(state.tuesdayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent =description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }
        if (state.wednesdayError != null) {
            var title: String = "Error en el horario del Miercoles"
            var description: String
            when(state.wednesdayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent =description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }
        if (state.thursdayError != null) {
            var title: String = "Error en el horario del Jueves"
            var description: String
            when(state.thursdayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent =description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }
        if (state.fridayError != null) {
            var title: String = "Error en el horario del Viernes"
            var description: String
            when(state.fridayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent =description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }
        if (state.saturdayError != null) {
            var title: String = "Error en el horario del Sabado"
            var description: String
            when(state.saturdayError){
                ConfigProfileAvailabilityState.DayError.StarTimeEmpty ->{
                    description = "El horario de inicio no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.EndTimeEmpty ->{
                    description = "El horario de fin no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.TimeEmpty ->{
                    description = "El rango horario no puede estar vacio."
                }
                ConfigProfileAvailabilityState.DayError.WrongTimeRange->{
                    description = "La fecha de inicio no puede ser posterior a la de fin."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent =description,
                onDismiss = {
                    onValueChange(ConfigProfileAvailabilityEvent.DismissDialog)
                }
            )
        }

    }
}









