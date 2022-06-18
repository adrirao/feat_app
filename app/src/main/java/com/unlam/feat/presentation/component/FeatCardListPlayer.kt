package com.unlam.feat.presentation.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.unlam.feat.model.Player

@Composable
fun FeatCardListPLayer(
    playerConfirmed: List<Player>,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    LazyColumn() {
        items(
            items = playerConfirmed,
            itemContent = { player ->
                FeatCardUser(
                    textNameUser = player.person.names + " " + player.person.lastname,
                    textPosition = player.position.description,
                    textLevel = player.level.description,
                    content = content
                )
            }
        )
    }
}