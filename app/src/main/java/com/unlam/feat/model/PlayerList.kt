package com.unlam.feat.model

data class PlayerList(
    val id: Int,
    val origin: String,
    val state: State,
    val event: Event,
    val player: Player,
    val date: String
)
