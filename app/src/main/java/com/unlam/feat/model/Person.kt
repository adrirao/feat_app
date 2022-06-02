package com.unlam.feat.model

data class Person(
    val id :Int,
    val lastname: String,
    val birthDate: String,
    val sex: String,
    val minAge: Int,
    val maxAge: Int,
    val nickname:String,
    val user : User,
    val player: List<Player>,
    val availability: List<Availability>,
    val address: List<Address>,
    val events: List<Event>,
)
