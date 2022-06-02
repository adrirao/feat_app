package com.unlam.feat.model

data class Player(
    val id: Int,
    val abilities: String,
    val notifications: Boolean,
    val person:Person,
    val sport:Sport,
    val position: Position,
    val level : Level,
    val valuation : Valuation,
    val created:String
)