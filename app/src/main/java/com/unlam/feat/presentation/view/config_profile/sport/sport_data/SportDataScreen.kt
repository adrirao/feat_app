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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.presentation.component.FeatButton
import com.unlam.feat.presentation.component.FeatDropDown
import com.unlam.feat.presentation.component.FeatText
import com.unlam.feat.presentation.component.FeatTextField


@Composable
fun SportDataScreen(
    navController: NavHostController,
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {

    SportContent(
        state,
        navigateToConfigSport = {
        navController.popBackStack()
        navController.navigate(Screen.ConfigSport.route)},
        onValueChange,
        sportGeneric
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SportContent(
    state: SportDataState,
    navigateToConfigSport: () -> Unit,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(top = 20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.ic_isologotype_2),
                contentDescription = stringResource(R.string.feat_logo)
            )
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                when(sportGeneric.id){
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

                FeatButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(60.dp),
                    textButton = "Guardar",
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    colorText = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    onClick = {
                        navigateToConfigSport()
                    }
                )


            }
        }
    }

}

@Composable
private fun SportDataSoccer(
    state: SportDataState,
    onValueChange: (SportDataEvent) -> Unit,
    sportGeneric: SportGeneric
){
    FeatText(text = sportGeneric.description)


    val optionsPosition: MutableList<String> = mutableListOf<String>()

    state.positionList.forEach { positionList ->
        if(positionList.sport.id == 1){
            optionsPosition.add(positionList.description)
        }
    }

    FeatDropDown(label = "Posición",optionsPosition,{})


    val optionsLevel: MutableList<String> = mutableListOf<String>()

    state.levelList.forEach { levelList ->

        if(levelList.sport.id == 1){
            optionsPosition.add(levelList.description)
        }
    }

    FeatDropDown(label = "Nivel",optionsLevel,{})



    val optionsValuation: MutableList<String> = mutableListOf<String>()

    state.valuationList.forEach { valuationList ->

        optionsValuation.add(valuationList.description)

    }

    FeatDropDown(label = "Grado de interés",optionsValuation,{})





    val textarea = remember { mutableStateOf("") }
    FeatTextField(
        textLabel = "Aptitudes",
        text = textarea.value,
        onValueChange={
            textarea.value = it
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
){
    FeatText(text = sportGeneric.description)
    val optionsPosition = listOf("Principiante", "Normal", "Bueno", "Competitivo")
    FeatDropDown(label = "Posición",optionsPosition,{})


    val optionsLevel = listOf("Principiante", "Normal", "Bueno", "Competitivo")
    FeatDropDown(label = "Nivel",optionsLevel,{})

    FeatTextField(
        textLabel = "Altura",
        keyboardType= KeyboardType.Number,
        text = "",
        onValueChange={

        }
    )
    val textarea = remember { mutableStateOf("") }
    FeatTextField(
        textLabel = "Aptitudes",
        text = textarea.value,
        onValueChange={
            textarea.value = it
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
){
    FeatText(text = sportGeneric.description)
    val optionsPosition = listOf("Principiante", "Normal", "Bueno", "Competitivo")
    FeatDropDown(label = "Posición",optionsPosition,{})


    val optionsLevel = listOf("Principiante", "Normal", "Bueno", "Competitivo")
    FeatDropDown(label = "Nivel",optionsLevel,{})

    val textarea = remember { mutableStateOf("") }
    FeatTextField(
        textLabel = "Aptitudes",
        text = textarea.value,
        onValueChange={
            textarea.value = it
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
){
    FeatText(text = sportGeneric.description)
    val optionsPosition = listOf("Principiante", "Normal", "Bueno", "Competitivo")
    FeatDropDown(label = "Posición",optionsPosition,{})


    val optionsLevel = listOf("Principiante", "Normal", "Bueno", "Competitivo")
    FeatDropDown(label = "Nivel",optionsLevel,{})

    val textarea = remember { mutableStateOf("") }
    FeatTextField(
        textLabel = "Aptitudes",
        text = textarea.value,
        onValueChange={
            textarea.value = it
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
){
    FeatText(text = sportGeneric.description)


    val textarea = remember { mutableStateOf("") }
    FeatTextField(
        textLabel = "Aptitudes",
        text = textarea.value,
        onValueChange={
            textarea.value = it
        },
        maxLines = 4,
        singleLine = false,
        maxLength = 150
    )
}

