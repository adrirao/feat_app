package com.unlam.feat.presentation.view.home.detail_event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unlam.feat.presentation.component.FeatHeader

@Preview(showSystemUi = true)
@Composable
fun DetailEventHome(
    state: DetailEventHomeState? = null
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")

    Column {
        Box(modifier = Modifier
            .background(MaterialTheme.colors.primary)) {
            Column() {
                FeatHeader(text = state!!.event!!.name)
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