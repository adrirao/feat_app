package com.unlam.feat.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text
import com.unlam.feat.presentation.ui.theme.textVariant


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeatCardUser(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
    elevation: Dp = 6.dp,
    backgroundColor: Color = MaterialTheme.colors.card,
    corner: CornerSize = CornerSize(16.dp),
    shape: RoundedCornerShape = RoundedCornerShape(corner),
    onClickCard: () -> Unit = {},
    textNameUser: String = "",
    colorTextNameUser: Color = MaterialTheme.colors.textVariant,
    fontSizeNameUser: TextUnit = 25.sp,
    fontWeightNameUser: FontWeight = FontWeight.Bold,
    textPosition: String = "",
    colorTextPosition: Color = MaterialTheme.colors.text,
    fontSizeTextPosition: TextUnit = 18.sp,
    fontWeightTextPosition: FontWeight = FontWeight.Medium,
    textLevel: String = "",
    colorTextLevel: Color = MaterialTheme.colors.text,
    fontSizeTextLevel: TextUnit = 18.sp,
    fontWeightTextLevel: FontWeight = FontWeight.Medium,
    painter: Painter = painterResource(id = R.drawable.nahuel),
    content: @Composable (RowScope.() -> Unit)? = null
    ) {
    Card(
        modifier = modifier,
        onClick = onClickCard,
        elevation = elevation,
        backgroundColor = backgroundColor,
        shape = shape
    ) {

        Column() {


        Row(){
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(shape)
                    .align(Alignment.CenterVertically)
                    .weight(5.0f)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Top)
                    .weight(10.0f)
            ) {
                Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)) {
                    FeatText(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = textNameUser,
                        color = colorTextNameUser,
                        fontSize = fontSizeNameUser,
                        fontWeight = fontWeightNameUser,
                    )
                }
                Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                    FeatText(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.Position),
                        color = MaterialTheme.colors.primary,
                        fontSize = fontSizeTextPosition,
                        fontWeight = fontWeightTextPosition,
                    )
                    FeatText(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = textPosition,
                        color = colorTextPosition,
                        fontSize = fontSizeTextPosition,
                        fontWeight = fontWeightTextPosition,
                    )
                }
                Row(modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                    FeatText(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.Level),
                        color = MaterialTheme.colors.primary,
                        fontSize = fontSizeTextPosition,
                        fontWeight = fontWeightTextPosition,
                    )
                    FeatText(
                        modifier = Modifier
                            .padding(10.dp, 0.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically),
                        text = textLevel,
                        color = colorTextLevel,
                        fontSize = fontSizeTextLevel,
                        fontWeight = fontWeightTextLevel,
                    )
                }
            }

        }
        if (content != null) {
        Row(
            modifier = Modifier
                .align(Alignment.End),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
                content()
            }
        }
        }
    }
}