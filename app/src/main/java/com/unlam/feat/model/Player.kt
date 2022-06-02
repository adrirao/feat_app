package com.unlam.feat.model

import com.google.android.gms.maps.model.CameraPosition

data class Player(
    val id: Int,
    val abilities: String,
    val notifications: Boolean,
    val person:Int,
    val sport:Int,
    val position: Int,
    val level : Int,
    val valuation : Int,
    val playerList: PlayerList,
    val created:String
)