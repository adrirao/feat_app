package com.unlam.feat.presentation.view.config_profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.view.config_profile.availability.ConfigProfileAvailabilityEvent
import com.unlam.feat.presentation.view.config_profile.availability.ConfigProfileAvailabilityState


@Composable
fun ConfigProfileAvailabilityScreen(
    navController: NavHostController,
    state: ConfigProfileAvailabilityState,
    onValueChange: (ConfigProfileAvailabilityEvent) -> Unit
) {
    ConfigProfileAvailabilityContent(state, navigateToConfigAdditionalInformation = {
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }, onValueChange)
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ConfigProfileAvailabilityContent(
    state: ConfigProfileAvailabilityState,
    navigateToConfigAdditionalInformation: () -> Unit,
    onValueChange: (ConfigProfileAvailabilityEvent) -> Unit
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
                text = "Ingresa tu disponibilidad horaria.",
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


                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {

                    val sundayIsChecked = remember { mutableStateOf(false) }
                    val mondayIsChecked = remember { mutableStateOf(false) }
                    val tuesdayIsChecked = remember { mutableStateOf(false) }
                    val wednesdayIsChecked = remember { mutableStateOf(false) }
                    val thursdayIsChecked = remember { mutableStateOf(false) }
                    val fridayIsChecked = remember { mutableStateOf(false) }
                    val saturdayIsChecked = remember { mutableStateOf(false) }

                    FeatAvailabilityCheckBoxPickerTime(
                        sundayIsChecked,
                        { sundayIsChecked.value = it },
                        "Domingo",
                        state.startTime1,
                        state.endTime1,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime1(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime1(it)) },
                    )
                    FeatAvailabilityCheckBoxPickerTime(
                        mondayIsChecked,
                        { mondayIsChecked.value = it },
                        "Lunes",
                        state.startTime2,
                        state.endTime2,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime2(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime2(it)) },
                    )

                    FeatAvailabilityCheckBoxPickerTime(
                        tuesdayIsChecked,
                        { tuesdayIsChecked.value = it },
                        "Martes",
                        state.startTime3,
                        state.endTime3,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime3(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime3(it)) },
                    )
                    FeatAvailabilityCheckBoxPickerTime(
                        wednesdayIsChecked,
                        { wednesdayIsChecked.value = it },
                        "Miercoles",
                        state.startTime4,
                        state.endTime4,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime4(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime4(it)) },
                    )
                    FeatAvailabilityCheckBoxPickerTime(
                        thursdayIsChecked,
                        { thursdayIsChecked.value = it },
                        "Jueves",
                        state.startTime5,
                        state.endTime5,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime5(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime5(it)) },
                    )
                    FeatAvailabilityCheckBoxPickerTime(
                        fridayIsChecked,
                        { fridayIsChecked.value = it },
                        "Viernes",
                        state.startTime6,
                        state.endTime6,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime6(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime6(it)) },
                    )
                    FeatAvailabilityCheckBoxPickerTime(
                        saturdayIsChecked,
                        { saturdayIsChecked.value = it },
                        "Sabado",
                        state.startTime7,
                        state.endTime7,
                        onValueChangeStartTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredStartTime7(it)) },
                        onValueChangeEndTime = { onValueChange(ConfigProfileAvailabilityEvent.EnteredEndTime7(it)) },
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









