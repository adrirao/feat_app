package com.unlam.feat.presentation.view.config_profile.personal_data


import android.annotation.SuppressLint
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
import com.google.accompanist.permissions.*
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter


@Composable
fun ConfigProfilePersonalDataScreen(
    navController: NavHostController,
    state: ConfigProfilePersonalDataState,
    onValueChange: (ConfigProfilePersonalDataEvent) -> Unit
) {
    ConfigProfilePersonalData(state, navigateToConfigProfileAddress = {
        navController.popBackStack()
        navController.navigate(Screen.ConfigProfileAddress.route)
    }, onValueChange)
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ConfigProfilePersonalData(
    state: ConfigProfilePersonalDataState,
    navigateToConfigProfileAddress: () -> Unit,
    onValueChange: (ConfigProfilePersonalDataEvent) -> Unit
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
                text = "Configuracion de perfil.",
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

                FeatTextField(
                    text = state.lastName,
                    onValueChange = {
                        onValueChange(ConfigProfilePersonalDataEvent.EnteredLastName(it))
                    },
                    textLabel = "Apellido"
                )

                FeatTextField(
                    text = state.name,
                    onValueChange = {
                        onValueChange(ConfigProfilePersonalDataEvent.EnteredName(it))
                    },
                    textLabel = "Nombres"
                )


                val dialogState = rememberMaterialDialogState()
                FeatTextField(
                    text = if (state.dateOfBirth != null) {
                        state.dateOfBirth!!.format(
                            DateTimeFormatter.ofPattern(
                                "dd LLLL yyyy"
                            )
                        )
                    } else {
                        " "
                    },
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { dialogState.show() },
                    enabled = false,
                    textLabel = "Fecha de nacimiento"

                )
                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton("Ok")
                        negativeButton("Cancel")
                    },

                    ) {
                    datepicker() { date ->
                        onValueChange(ConfigProfilePersonalDataEvent.EnteredDateOfBirth(date))

                    }
                }

                FeatTextField(
                    text = state.nickname,
                    onValueChange = {
                        onValueChange(ConfigProfilePersonalDataEvent.EnteredNickname(it))
                    },
                    textLabel = "Apodo"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    FeatText(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(), text = "Sexo"
                    )
                    val sexs = listOf("", "Hombre", "Mujer", "Otro")
                    val currentSelection = remember { mutableStateOf(sexs.first()) }
                    Row {
                        FeatRadioGroup(
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            items = sexs,
                            selection = currentSelection.value,
                            onItemClick = { clickedItem ->
                                currentSelection.value = clickedItem
                                onValueChange(ConfigProfilePersonalDataEvent.EnteredSex(clickedItem))
                            }
                        )
                    }


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
                        navigateToConfigProfileAddress()
                        //persistir en la base
                    }
                )


            }
        }


    }

}








