package com.unlam.feat.model.request


data class RequestSearchEventApply(
    val userUid: String,
    val eventId: Int,
    val origin: String
)
