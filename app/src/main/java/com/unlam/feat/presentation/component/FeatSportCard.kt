package com.unlam.feat.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeatSportCard(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
    elevation: Dp = 6.dp,
    backgroundColor: Color = Color.White,
    corner: CornerSize = CornerSize(16.dp),
    shape: RoundedCornerShape = RoundedCornerShape(corner),
    onClickCard: () -> Unit,

    //ICON SPORT
    sport: String = "",
    sportDescription: String = "",
) {

    Card(
        modifier = modifier,
        onClick = onClickCard,
        elevation = elevation,
        backgroundColor = backgroundColor,
        shape = shape
    ) {
        when (sport) {
            "soccer" -> contentCard(
                textSport = "Futbol",
                shape = shape,
                sportDescription = sportDescription,
                routeImage = R.drawable.soccer
            )
            "basketball" -> contentCard(
                textSport = "Basquet",
                shape = shape,
                sportDescription = sportDescription,
                routeImage = R.drawable.basketball
            )
            "tennis" -> contentCard(
                textSport = "Tenis",
                shape = shape,
                sportDescription = sportDescription,
                routeImage = R.drawable.tennis
            )
            "padel" -> contentCard(
                textSport = "Padel",
                shape = shape,
                sportDescription = sportDescription,
                routeImage = R.drawable.padel
            )
            "recreationalEvent" -> contentCard(
                textSport = "Evento recreativo",
                shape = shape,
                sportDescription = sportDescription,
                routeImage = R.drawable.recreational_event
            )
        }

    }
}

@Composable
private fun contentCard(
    textSport: String,
    shape: RoundedCornerShape,
    sportDescription: String,
    routeImage: Int
) {
    Row() {
        FeatText(
            modifier = Modifier
                .weight(10.0f)
                .align(Alignment.CenterVertically)
                .padding(20.dp),
            text = textSport,
            color = Color.Black,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )
        Image(
            painter = painterResource(id = routeImage),
            contentDescription = sportDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .clip(shape)
                .align(Alignment.CenterVertically)
                .weight(4.0f)
        )
    }
}