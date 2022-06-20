package com.unlam.feat.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.common.Constants
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text
import com.unlam.feat.presentation.ui.theme.textVariant


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeatCard(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
    elevation: Dp = 6.dp,
    backgroundColor: Color = MaterialTheme.colors.card,
    corner: CornerSize = CornerSize(16.dp),
    shape: RoundedCornerShape = RoundedCornerShape(corner),
    onClickCard: () -> Unit = {},
    sport: String = "",
    sportDescription: String = "",
    textNameEvent: String = "",
    colorTextNameEvent: Color = MaterialTheme.colors.textVariant,
    fontSizeNameEvent: TextUnit = 25.sp,
    fontWeightNameEvent: FontWeight = FontWeight.Bold,
    textDay: String = "",
    colorTextDay: Color = MaterialTheme.colors.text,
    fontSizeTextDay: TextUnit = 15.sp,
    fontWeightTextDay: FontWeight = FontWeight.Medium,
    textLocation: String = "",
    colorTextLocation: Color = MaterialTheme.colors.text,
    fontSizeTextLocation: TextUnit = 15.sp,
    fontWeightTextLocation: FontWeight = FontWeight.Medium,
    textState: String = "",
    colorTextState: Color = Color(0xFF019C50),
    fontSizeTextState: TextUnit = 20.sp,
    fontWeightTextState: FontWeight = FontWeight.Black,
    onClickChatButton: () -> Unit = {},
    chatEnable:Boolean = true
) {
    Card(
        modifier = modifier,
        onClick = onClickCard,
        elevation = elevation,
        backgroundColor = backgroundColor,
        shape = shape
    ) {
        Row {
            when (sport) {
                Constants.Sports.SOCCER_5, Constants.Sports.SOCCER_6,
                Constants.Sports.SOCCER_7,Constants.Sports.SOCCER_9,Constants.Sports.SOCCER_11->  Image(
                    painter = painterResource(id = R.drawable.soccer),
                    contentDescription = sportDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(shape)
                        .align(Alignment.CenterVertically)
                        .weight(5.0f)
                )
                Constants.Sports.BASKETBALL ->  Image(
                    painter = painterResource(id = R.drawable.basketball),
                    contentDescription = sportDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(shape)
                        .align(Alignment.CenterVertically)
                        .weight(5.0f)
                )
                Constants.Sports.TENNIS_SINGLE,Constants.Sports.TENNIS_DOUBLES ->  Image(
                    painter = painterResource(id = R.drawable.tennis),
                    contentDescription = sportDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(shape)
                        .align(Alignment.CenterVertically)
                        .weight(5.0f)
                )
                Constants.Sports.PADDLE_SINGLE,Constants.Sports.PADDLE_DOUBLE ->  Image(
                    painter = painterResource(id = R.drawable.padel),
                    contentDescription = sportDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(shape)
                        .align(Alignment.CenterVertically)
                        .weight(5.0f)
                )
                Constants.Sports.RECREATIONAL_ACTIVITY ->{
                    if (!isSystemInDarkTheme()){
                        val id = R.drawable.recreational_activity
                    }
                    Image(
                    painter = painterResource(id = R.drawable.recreational_activity_white),
                    contentDescription = sportDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(shape)
                        .align(Alignment.CenterVertically)
                        .weight(5.0f)
                )}
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Top)
                    .weight(10.0f)
            ) {
                Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clip(shape)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar",
                        contentScale = ContentScale.Fit,
                    )
                    Text(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = textNameEvent,
                        color = colorTextNameEvent,
                        fontSize = fontSizeNameEvent,
                        fontWeight = fontWeightNameEvent,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clip(shape)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.watch),
                        contentDescription = "Day",
                        contentScale = ContentScale.Fit,
                    )
                    Text(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = textDay,
                        color = colorTextDay,
                        fontSize = fontSizeTextDay,
                        fontWeight = fontWeightTextDay,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clip(shape)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.logotipo),
                        contentDescription = "Location",
                        contentScale = ContentScale.Fit,
                    )
                    Text(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = textLocation,
                        color = colorTextLocation,
                        fontSize = fontSizeTextLocation,
                        fontWeight = fontWeightTextLocation,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                    Text(
                        text = textState,
                        color = colorTextState,
                        fontSize = fontSizeTextState,
                        fontWeight = fontWeightTextState,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
            if(chatEnable){
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                    .weight(3.0f)
            ) {
                IconButton(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.End),
                    onClick = onClickChatButton
                ) {
                    if (isSystemInDarkTheme()){
                        Icon(
                            painterResource(id = R.drawable.chat_white),
                            contentDescription = "chat button",
                        )
                    }else{
                        Icon(
                            painterResource(id = R.drawable.chat),
                            contentDescription = "chat button",
                        )
                    }

                }


            }
            }
        }
    }
}