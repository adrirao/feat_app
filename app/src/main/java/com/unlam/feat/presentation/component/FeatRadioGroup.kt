package com.unlam.feat.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun FeatRadioGroup(
    modifier: Modifier,
    items: List<String>,
    selection: String?,
    onItemClick: ((String) -> Unit)
) {
    Row(modifier = modifier) {
        items.forEach { item ->
            if (item != "") {
                FeatRadioButton(
                    modifier =modifier,
                    label = item,
                    selected = item == selection,
                    onClick = {
                        onItemClick(item)
                    }
                )
            }
        }
    }
}
