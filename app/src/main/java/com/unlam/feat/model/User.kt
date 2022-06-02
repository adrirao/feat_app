package com.unlam.feat.model

import com.google.gson.annotations.SerializedName

data class User(
    val uid:String,
    val email:String,
    @SerializedName("user_type")
    val userType: UserType,
    val person: Person
)
