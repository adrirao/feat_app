package com.unlam.feat.presentation.view.events.add_event

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.FeatButton
import com.unlam.feat.presentation.component.FeatSportCard
import com.unlam.feat.presentation.component.FeatText

@Composable
fun SportsScreen(
    navController: NavHostController,
) {

    SportsContent(navigateToAddEvent = {
        navController.popBackStack()
        navController.navigate(Screen.AddEvent.route)
    })

}

@Composable
fun SportsContent(
    navigateToAddEvent: () -> Unit
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
                    text = stringResource(R.string.text_select_sport),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
                    .verticalScroll(rememberScrollState())
                    .weight(8.0f)
            ) {
                FeatSportCard(
                    onClickCard = {navigateToAddEvent()},
                    sport = stringResource(R.string.text_soccer),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                FeatSportCard(
                    onClickCard = {navigateToAddEvent()},
                    sport = stringResource(R.string.text_basketball),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                FeatSportCard(
                    onClickCard = {navigateToAddEvent()},
                    sport = stringResource(R.string.text_tennis),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                FeatSportCard(
                    onClickCard = {navigateToAddEvent()},
                    sport = stringResource(R.string.text_paddle),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                FeatSportCard(
                    onClickCard = {navigateToAddEvent()},
                    sport = stringResource(R.string.text_recreational_event),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
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
                    textButton = stringResource(R.string.text_finish),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    colorText = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }
        }

    }
}