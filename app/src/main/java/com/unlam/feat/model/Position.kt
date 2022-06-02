package com.unlam.feat.model

data class Position(
    val id :Int,
    val description:String,
    val sport: Int,
    val player: List<Player>
)
