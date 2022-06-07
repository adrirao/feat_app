package com.unlam.feat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun FeatTabRow(
    nameHeader : String
){
    var tabIndex by remember { mutableStateOf(0) } // 1.

    Column {
        Box(modifier = Modifier
            .background(MaterialTheme.colors.primary)) {
            Column() {
                FeatHeader(text = nameHeader)
                val tabTitles = listOf("Descripcion", "Participantes")
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index,
                            modifier = Modifier.background(MaterialTheme.colors.primary),
                            onClick = {
                                tabIndex = index
                            },
                            text = { Text(text = title) }
                        )
                    }
                }
            }

        }
        when (tabIndex) {
            0 -> Text("Hola")
            1 -> Text("Mundo")
        }
    }
}