package com.unlam.feat.model.response

import com.unlam.feat.model.*

data class ResponseDetailEvent(
    val event: Event,
    val players: List<Player>
)

