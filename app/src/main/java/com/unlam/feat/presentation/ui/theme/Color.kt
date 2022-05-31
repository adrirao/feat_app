package com.unlam.feat.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color

val PurpleAccent = Color(0xFF673ab7)
val PurpleAccentVariant = Color(0xFF673ab7)
val LightGreenAccent = Color(0xFF00E676)
val GreyBackground = Color(0xFFE4E4E4)

val Colors.text: Color
    get() = if (isLight) Color(0xFF000000) else Color(0xFFFFFFFF)

val Colors.card: Color
    get() = if (isLight) Color(0xFFE4E4E4) else Color(0xFF252525)

val Colors.textVariant : Color
    get() = if(isLight) Color(0xFF673ab7) else Color(0xFF00E676)