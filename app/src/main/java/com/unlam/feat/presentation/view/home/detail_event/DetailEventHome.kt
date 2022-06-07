package com.unlam.feat.presentation.view.home.detail_event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unlam.feat.presentation.component.FeatHeader
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.card

@Composable
fun DetailEventHome(
    state: DetailEventHomeState? = null
) {
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("Descripcion", "Participantes")

    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
        ) {
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
            0 -> DescriptionEvent(state!!)
            1 -> LazyColumn() {
                items(
                    items = state?.players!!,
                    itemContent = { player ->
                        Text(text = player.id.toString())
                    }
                )

            }
        }
    }
}

@Composable
fun DescriptionEvent(state: DetailEventHomeState) {
    Box(
        modifier = Modifier
            .height(500.dp)
            .fillMaxSize()
            .padding(20.dp)
            .clip(Shapes.large)
            .background(MaterialTheme.colors.card),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = state.event!!.description)
        }
    }
}