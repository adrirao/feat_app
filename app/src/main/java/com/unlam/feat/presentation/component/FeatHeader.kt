package com.unlam.feat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.presentation.ui.theme.text

@Composable
fun FeatHeader(text: String,dividerOn:Boolean = true) {
    Column(
        modifier = Modifier
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(10.dp)
        ) {
            FeatText(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp
            )

        }

    }
    if(dividerOn){
    Divider(
        thickness = 3.dp,
        modifier = Modifier.padding(vertical = 0.dp),
        color = MaterialTheme.colors.secondary
    )
    }
}