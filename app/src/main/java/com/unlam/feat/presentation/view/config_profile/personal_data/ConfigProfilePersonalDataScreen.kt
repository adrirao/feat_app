package com.unlam.feat.presentation.view.config_profile.personal_data


import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomEnd
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
import com.google.accompanist.permissions.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.view.register.RegisterEvent
import com.unlam.feat.presentation.view.register.RegisterState
import com.unlam.feat.presentation.view.register.RegisterViewModel
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


    if (state.isLoading) {
        FeatCircularProgress()
    }

    SetMessages(
        state,
        navController,
        onValueChange
    )

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
    if (state.isSuccessSubmitData) {
        LaunchedEffect(true) {
            navigateToConfigProfileAddress()
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
                    text = "Configuracion de perfil.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                FeatText(
                    text = "Ingrese sus datos personales. 1/5",
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
            }
            Row(
                modifier = Modifier
                    .align(End)
                    .weight(0.1f, false),
                verticalAlignment = Alignment.Bottom
            ) {
                FeatButtonRounded(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(60.dp),
                    drawable = R.drawable.arrow_next,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = {
                        onValueChange(ConfigProfilePersonalDataEvent.SubmitData)

                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }

        }

    }

}

@Composable
private fun SetMessages(
    state: ConfigProfilePersonalDataState,
    navController: NavController,
    onValueChange: (ConfigProfilePersonalDataEvent) -> Unit
) {
    if (state.error != null) {
        FeatAlertDialog(
            title = "Error de conexión",
            descriptionContent = "No se ha podido conectar con el servidor, vuelva a intentarlo.",
            onDismiss = {
                onValueChange(ConfigProfilePersonalDataEvent.DismissDialog)
                onValueChange(ConfigProfilePersonalDataEvent.SingOutUser)
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )
    }

    var countFieldEmptys: List<String> = state.fieldEmpty.split(',')


    if (state.fieldEmpty != "" && countFieldEmptys.size > 2) {
        FeatAlertDialog(
            title = "Hay campos vacios",
            descriptionContent = "Por favor, verifica que los siguientes campos no esten vacios ${state.fieldEmpty}",
            onDismiss = {
                onValueChange(ConfigProfilePersonalDataEvent.DismissDialog)
            }
        )
    } else {
        if (state.nameError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese un nombre en el campo",
                onDismiss = {
                    onValueChange(ConfigProfilePersonalDataEvent.DismissDialog)
                }
            )
        }

        if (state.lastNameError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese un apellido en el campo",
                onDismiss = {
                    onValueChange(ConfigProfilePersonalDataEvent.DismissDialog)
                }
            )
        }

        if (state.sexError != null) {
            FeatAlertDialog(
                title = "Debe seleccionar un sexo",
                descriptionContent = "Por favor, seleccione una opción en sexo",
                onDismiss = {
                    onValueChange(ConfigProfilePersonalDataEvent.DismissDialog)
                }
            )
        }

        if (state.dateOfBirthError != null) {
            var title: String = "Error en la fecha ingresada"
            var description: String
            when (state.dateOfBirthError) {
                ConfigProfilePersonalDataState.DateOfBirthError.FieldEmpty -> {
                    description = "El campo no puede estar vacío."
                }
                ConfigProfilePersonalDataState.DateOfBirthError.IsInvalid -> {
                    description = "Debe ser mayor de 16 para utilizar la app."
                }
            }
            FeatAlertDialog(
                title = title,
                descriptionContent = description,
                onDismiss = {
                    onValueChange(ConfigProfilePersonalDataEvent.DismissDialog)
                }
            )
        }
    }
}










