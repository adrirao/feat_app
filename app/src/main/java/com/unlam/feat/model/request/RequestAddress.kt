package com.unlam.feat.model.request

import com.google.gson.annotations.SerializedName

data class RequestAddress (
    val alias:String,
    val street: String,
    val number: String,
    val town: String,
    @SerializedName("zip_code")
    val zipCode: String,
    val latitude: String,
    val logitude: String,
    val person: Int
)
