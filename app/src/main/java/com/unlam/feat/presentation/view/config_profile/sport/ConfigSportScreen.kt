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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unlam.feat.common.Screen
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.presentation.component.FeatButton
import com.unlam.feat.presentation.component.FeatCircularProgress
import com.unlam.feat.presentation.component.FeatSportCard
import com.unlam.feat.presentation.component.FeatText
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataState


@Composable
fun ConfigSportScreen(
    navController: NavHostController,
    state: ConfigSportState,
    onValueChange: (ConfigSportEvent) -> Unit,
//    sportGenericId:String?
) {

    ConfigSportContent(
        state,
        navigateToSportData = { sportGeneric ->
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(SportGeneric::class.java).lenient()
            val sportGenericJson = jsonAdapter.toJson(sportGeneric)

            navController.popBackStack()
            navController.navigate(Screen.SportData.route + "/" + sportGenericJson)
        },
        onValueChange,
        /*sportGenericId*/
    )
}

@Composable
fun ConfigSportContent(
    state: ConfigSportState,
    navigateToSportData: (SportGeneric) -> Unit,
    onValueChange: (ConfigSportEvent) -> Unit,
    /* sportGenericId:String?*/
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(top = 20.dp)
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
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Selecciona tus deportes",
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
                ) {

                    if (state.sportsList.isNotEmpty()) {
                        state.sportsList.forEach { sportGeneric ->


                            FeatSportCard(
                                onClickCard = { navigateToSportData(sportGeneric) },
                                sport = sportGeneric.description,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .background(color = MaterialTheme.colors.secondary),
                                idSport = sportGeneric.id
                            )

                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .weight(1.0f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    FeatButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(60.dp),
                        textButton = "Finalizar",
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        colorText = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                    )
                }
            }
        }

    }
}