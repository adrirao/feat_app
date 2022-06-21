package com.unlam.feat.model.response

import com.unlam.feat.model.Event
import com.unlam.feat.model.Player

data class ResponseDataSearchEvent(
    val event: Event,
    val playersConfirmed: List<Player>,
    )