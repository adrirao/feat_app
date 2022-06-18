package com.unlam.feat.presentation.view.config_profile.sport.sport_data

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.model.ListSportId
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataState


@Composable
fun SportDataScreen(
    navController: NavHostController,
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric,
    listSportGenericId: ListSportId
) {

    if (state.isLoading) {
        FeatCircularProgress()
    }

    SetMessagesSportData(
        state = state,
        navController = navController,
        onValueChange = onValueChange,

    )

    SportContent(
        state,
        navigateToConfigSport = {
            listSportGenericId.idList.add(sportGeneric.id.toString())
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapterList = moshi.adapter(ListSportId::class.java).lenient()
            val listSportDisableJson = jsonAdapterList.toJson(listSportGenericId)
            navController.popBackStack()
            navController.navigate(Screen.ConfigSport.route + "?listSportGenericId=${listSportDisableJson}")
        },
        navigateToConfigSportError = {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapterList = moshi.adapter(ListSportId::class.java).lenient()
            val listSportDisableJson = jsonAdapterList.toJson(listSportGenericId)
            navController.popBackStack()
            navController.navigate(Screen.ConfigSport.route + "?listSportGenericId=${listSportDisableJson}")
        },
        onValueChange,
        sportGeneric
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SportContent(
    state: SportDataState,
    navigateToConfigSport: () -> Unit,
    navigateToConfigSportError: () -> Unit,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
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
            .padding(top = 20.dp)
    ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        .weight(weight = 0.125f, fill = false)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    FeatText(
                        text = "Ingrese sus datos.",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 14.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                            .height(1.dp)
                            .width(250.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 0.8f, fill = true),
//                        .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.Center,

                    ) {


                    when (sportGeneric.id) {
                        1 -> {
                            SportDataSoccer(
                                state,
                                onValueChange,
                                sportGeneric
                            )
                        }
                        2 -> {
                            SportDataPadel(
                                state,
                                onValueChange,
                                sportGeneric
                            )
                        }
                        3 -> {
                            SportDataTennis(
                                state,
                                onValueChange,
                                sportGeneric
                            )
                        }
                        4 -> {
                            SportDataBasketball(
                                state,
                                onValueChange,
                                sportGeneric
                            )
                        }
                        5 -> {
                            SportDataRecreationalActivity(
                                state,
                                onValueChange,
                                sportGeneric
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                        .weight(0.15f, false)
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(

                    ) {
                        FeatButtonRounded(
                            modifier = Modifier
                                .size(60.dp)
                                .fillMaxWidth(),
                            drawable = R.drawable.cancel,
                            colors = ButtonDefaults.buttonColors(Color(0xFFBB3131)),
                            onClick = {
                                navigateToConfigSportError()

                            },
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
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
                            onClick = {
                            onValueChange(SportDataEvent.SubmitData)
                            },
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                        )
                    }

                }

            }


        }
    }



@Composable
private fun SportDataSoccer(
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {

    val optionsPosition: MutableList<String> = mutableListOf<String>()
    state.positionList.forEach { positionList ->
        optionsPosition.add(positionList.description)
    }
    FeatDropDown(label = "Posición", optionsPosition) { positionText ->
        state.positionList.forEach { position ->
            if (position.description == positionText) {
                onValueChange(SportDataEvent.SelectedPosition(position.id))
            }
        }
    }


    val optionsLevel: MutableList<String> = mutableListOf<String>()
    state.levelList.forEach { levelList ->
        optionsLevel.add(levelList.description)
    }
    FeatDropDown(label = "Nivel", optionsLevel) { levelText ->
        state.levelList.forEach { level ->
            if (level.description == levelText) {
                onValueChange(SportDataEvent.SelectedLevel(level.id))
            }
        }
    }


    val optionsValuation: MutableList<String> = mutableListOf<String>()
    state.valuationList.forEach { valuationList ->
        optionsValuation.add(valuationList.description)
    }
    FeatDropDown(label = "Grado de interés", optionsValuation) { valuationText ->
        state.valuationList.forEach { valuation ->
            if (valuation.description == valuationText) {
                onValueChange(SportDataEvent.SelectedValuation(valuation.id))
            }
        }
    }

    FeatTextField(
        textLabel = "Aptitudes",
        text = state.abilities,
        onValueChange = {
            onValueChange(SportDataEvent.EnteredAbilities(it))
        },
        maxLines = 4,
        singleLine = false,
        maxLength = 150
    )


}

@Composable
private fun SportDataBasketball(
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {
    val optionsPosition: MutableList<String> = mutableListOf<String>()
    state.positionList.forEach { positionList ->
        optionsPosition.add(positionList.description)
    }
    FeatDropDown(label = "Posición", optionsPosition) { positionText ->
        state.positionList.forEach { position ->
            if (position.description == positionText) {
                onValueChange(SportDataEvent.SelectedPosition(position.id))
            }
        }
    }

    val optionsLevel: MutableList<String> = mutableListOf<String>()
    state.levelList.forEach { levelList ->
        optionsLevel.add(levelList.description)
    }
    FeatDropDown(label = "Nivel", optionsLevel) { levelText ->
        state.levelList.forEach { level ->
            if (level.description == levelText) {
                onValueChange(SportDataEvent.SelectedLevel(level.id))
            }
        }
    }

    val optionsValuation: MutableList<String> = mutableListOf<String>()
    state.valuationList.forEach { valuationList ->
        optionsValuation.add(valuationList.description)
    }
    FeatDropDown(label = "Grado de interés", optionsValuation) { valuationText ->
        state.valuationList.forEach { valuation ->
            if (valuation.description == valuationText) {
                onValueChange(SportDataEvent.SelectedValuation(valuation.id))
            }
        }
    }


//    FeatTextField(
//        textLabel = "Altura",
//        keyboardType = KeyboardType.Number,
//        text = "",
//        onValueChange = {
//
//        }
//    )

    FeatTextField(
        textLabel = "Aptitudes",
        text = state.abilities,
        onValueChange = {
            onValueChange(SportDataEvent.EnteredAbilities(it))
        },
        maxLines = 4,
        singleLine = false,
        maxLength = 150
    )
}

@Composable
private fun SportDataTennis(
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {
    val optionsPosition: MutableList<String> = mutableListOf<String>()
    state.positionList.forEach { positionList ->
        optionsPosition.add(positionList.description)
    }
    FeatDropDown(label = "Posición", optionsPosition) { positionText ->
        state.positionList.forEach { position ->
            if (position.description == positionText) {
                onValueChange(SportDataEvent.SelectedPosition(position.id))
            }
        }
    }

    val optionsLevel: MutableList<String> = mutableListOf<String>()
    state.levelList.forEach { levelList ->
        optionsLevel.add(levelList.description)
    }
    FeatDropDown(label = "Nivel", optionsLevel) { levelText ->
        state.levelList.forEach { level ->
            if (level.description == levelText) {
                onValueChange(SportDataEvent.SelectedLevel(level.id))
            }
        }
    }
    val optionsValuation: MutableList<String> = mutableListOf<String>()
    state.valuationList.forEach { valuationList ->
        optionsValuation.add(valuationList.description)
    }
    FeatDropDown(label = "Grado de interés", optionsValuation) { valuationText ->
        state.valuationList.forEach { valuation ->
            if (valuation.description == valuationText) {
                onValueChange(SportDataEvent.SelectedValuation(valuation.id))
            }
        }
    }

    FeatTextField(
        textLabel = "Aptitudes",
        text = state.abilities,
        onValueChange = {
            onValueChange(SportDataEvent.EnteredAbilities(it))
        },
        maxLines = 4,
        singleLine = false,
        maxLength = 150
    )
}

@Composable
private fun SportDataPadel(
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {
    val optionsPosition: MutableList<String> = mutableListOf<String>()
    state.positionList.forEach { positionList ->
        optionsPosition.add(positionList.description)
    }
    FeatDropDown(label = "Posición", optionsPosition) { positionText ->
        state.positionList.forEach { position ->
            if (position.description == positionText) {
                onValueChange(SportDataEvent.SelectedPosition(position.id))
            }
        }
    }

    val optionsLevel: MutableList<String> = mutableListOf<String>()
    state.levelList.forEach { levelList ->
        optionsLevel.add(levelList.description)
    }
    FeatDropDown(label = "Nivel", optionsLevel) { levelText ->
        state.levelList.forEach { level ->
            if (level.description == levelText) {
                onValueChange(SportDataEvent.SelectedLevel(level.id))
            }
        }
    }
    val optionsValuation: MutableList<String> = mutableListOf<String>()
    state.valuationList.forEach { valuationList ->
        optionsValuation.add(valuationList.description)
    }
    FeatDropDown(label = "Grado de interés", optionsValuation) { valuationText ->
        state.valuationList.forEach { valuation ->
            if (valuation.description == valuationText) {
                onValueChange(SportDataEvent.SelectedValuation(valuation.id))
            }
        }
    }

    FeatTextField(
        textLabel = "Aptitudes",
        text = state.abilities,
        onValueChange = {
            onValueChange(SportDataEvent.EnteredAbilities(it))
        },
        maxLines = 4,
        singleLine = false,
        maxLength = 150
    )
}

@Composable
private fun SportDataRecreationalActivity(
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {


    state.positionList.forEach { positionList ->
        if(positionList.description == "No Aplica"){
          onValueChange(SportDataEvent.SelectedPosition(positionList.id))
        }
    }
    state.levelList.forEach { positionList ->
        if(positionList.description == "No Aplica"){
            onValueChange(SportDataEvent.SelectedLevel(positionList.id))
        }
    }



    val optionsValuation: MutableList<String> = mutableListOf<String>()
    state.valuationList.forEach { valuationList ->
        optionsValuation.add(valuationList.description)
    }
    FeatDropDown(label = "Grado de interés", optionsValuation) { valuationText ->
        state.valuationList.forEach { valuation ->
            if (valuation.description == valuationText) {
                onValueChange(SportDataEvent.SelectedValuation(valuation.id))
            }
        }
    }

    FeatTextField(
        textLabel = "Aptitudes",
        text = state.abilities,
        onValueChange = {
            onValueChange(SportDataEvent.EnteredAbilities(it))
        },
        maxLines = 4,
        singleLine = false,
        maxLength = 150
    )
}

@Composable
private fun SetMessagesSportData(
    state: SportDataState,
    navController: NavController,
    onValueChange: (SportDataEvent) -> Unit
) {
    if (state.error != "") {
        FeatAlertDialog(
            title = "Error de conexión",
            descriptionContent = "No se ha podido conectar con el servidor, vuelva a intentarlo.",
            onDismiss = {
                onValueChange(SportDataEvent.DismissDialog)
                onValueChange(SportDataEvent.SingOutUser)
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
                onValueChange(SportDataEvent.DismissDialog)
            }
        )
    } else {
        if (state.abilitiesError) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese aptitudes para el deporte en el campo",
                onDismiss = {
                    onValueChange(SportDataEvent.DismissDialog)
                }
            )
        }

        if (state.positionIdError && state.sportGenericId != 5) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, seleccione una posición para el deporte",
                onDismiss = {
                    onValueChange(SportDataEvent.DismissDialog)
                }
            )
        }

        if (state.levelIdError && state.sportGenericId != 5) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, seleccione una opción en el nivel de deporte",
                onDismiss = {
                    onValueChange(SportDataEvent.DismissDialog)
                }
            )
        }

        if (state.valuationIdError) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, seleccione el grado de interés del deporte",
                onDismiss = {
                    onValueChange(SportDataEvent.DismissDialog)
                }
            )
        }
    }
}






