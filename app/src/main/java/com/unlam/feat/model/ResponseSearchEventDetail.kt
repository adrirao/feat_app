package com.unlam.feat.model
import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class ResponseSearchEventDetail
(
    val event: Event,
    val playersConfirmed: List<Player>
)
