package com.unlam.feat.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FeatButton(
    modifier: Modifier = Modifier.fillMaxWidth()
        .padding(10.dp)
        .height(60.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(Color(0xFF5f7fd3)),
    textButton: String = "",
    @DrawableRes drawable: Int? = null,
    colorText: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start,
    onClick: () -> Unit = {},
    colorFilter: ColorFilter = ColorFilter.tint(Color.White),
    enabled:Boolean = true
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
        shape = RoundedCornerShape(10),
        enabled = enabled
    ) {

        if (textAlign != TextAlign.Start) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (drawable != null) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(drawable),
                            contentDescription = "Button $textButton",
                            colorFilter = colorFilter
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    FeatText(
                        fontWeight = FontWeight.Bold,
                        text = textButton,
                        color = colorText,
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (drawable != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(drawable),
                            contentDescription = "Button $textButton",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FeatText(
                        fontWeight = FontWeight.Bold,
                        text = textButton,
                        color = colorText,
                    )
                }
            }
        }

    }
}

@Composable
fun FeatButtonRounded(
    modifierImage:Modifier = Modifier,
    modifier: Modifier = Modifier.size(75.dp).padding(5.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(Color(0xFF5f7fd3)),
    @DrawableRes drawable: Int? = null,
    onClick: () -> Unit = {},
    colorFilter: ColorFilter? = ColorFilter.tint(Color.White)
) {
    Button(
        modifier = modifier,
        colors = colors,
        shape = RoundedCornerShape(50),
        onClick = onClick
    ) {
        Image(
            modifier = modifierImage,
            painter = painterResource(drawable!!),
            contentDescription = "Button",
            colorFilter = colorFilter,


        )
    }
}