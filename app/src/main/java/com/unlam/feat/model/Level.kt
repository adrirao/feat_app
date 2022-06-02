package com.unlam.feat.model

data class Level(
    val id: Int,
    val description: String,
    val player: List<Player>,
    val sport: Int,
)
