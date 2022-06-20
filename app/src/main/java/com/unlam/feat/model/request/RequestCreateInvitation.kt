package com.unlam.feat.model.request

data class RequestCreateInvitation(
    val userUid: String,
    val eventId: Int,
    val origin: String
)