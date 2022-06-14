package com.unlam.feat.presentation.view.config_profile.sport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.model.ListSportId
import com.unlam.feat.presentation.component.*


@Composable
fun ConfigSportScreen(
    navController: NavHostController,
    state: ConfigSportState,
    onValueChange: (ConfigSportEvent) -> Unit,
    listSportGenericId: ListSportId?
) {
    var listSportDisable: ListSportId

    if (listSportGenericId == null) {
        listSportDisable = ListSportId()
    } else {
        listSportDisable = listSportGenericId
    }


    ConfigSportContent(
        state,
        navigateToSportData = { sportGeneric ->
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(SportGeneric::class.java).lenient()
            val sportGenericJson = jsonAdapter.toJson(sportGeneric)
            val jsonAdapterList = moshi.adapter(ListSportId::class.java).lenient()
            val listSportDisableJson = jsonAdapterList.toJson(listSportDisable)
            navController.popBackStack()
            navController.navigate(Screen.SportData.route + "/sportGeneric=${sportGenericJson}&listSportGenericId=${listSportDisableJson}")
        },
        navigateToHome = {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        },
        onValueChange,
        listSportDisable
    )
}

@Composable
fun ConfigSportContent(
    state: ConfigSportState,
    navigateToSportData: (SportGeneric) -> Unit,
    navigateToHome: () -> Unit,
    onValueChange: (ConfigSportEvent) -> Unit,
    listSportDisable: ListSportId
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding( top = 5.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.5f)
            ) {
                FeatText(
                    modifier = Modifier.padding(bottom = 5.dp, top = 5.dp),
                    text = "Selecciona tus deportes. 5/5",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }
            if (state.isLoading) {
                FeatCircularProgress()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.surface)
                        .verticalScroll(rememberScrollState())
                        .weight(8.0f)
                        .padding(end = 10.dp)
                ) {

                    if (state.sportsList.isNotEmpty()) {
                        state.sportsList.forEach { sportGeneric ->

                            if (!listSportDisable.idList.contains(sportGeneric.id.toString())) {
                                FeatSportCard(
                                    onClickCard = { navigateToSportData(sportGeneric) },
                                    sport = sportGeneric.description,
                                    modifier = Modifier
                                        .height(100.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp,),
                                    idSport = sportGeneric.id
                                )
                            }
                        }
                    }
                }
                if (listSportDisable.idList.size > 0) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.End)
                            .weight(1f, false)
                            .align(Alignment.CenterHorizontally)
                            .padding(end = 20.dp, top = 10.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        FeatButtonRounded(
                            modifier = Modifier
                                .size(60.dp),
                            drawable = R.drawable.check,
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                            onClick = {
                                navigateToHome()
                            },
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                        )
                    }


                }
            }
        }

    }
}