package com.unlam.feat.model

data class UserType(
    val id: Int,
    val description: String,
    val users: List<User>
)
