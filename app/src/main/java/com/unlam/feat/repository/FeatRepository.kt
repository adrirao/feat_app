package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.Event
import kotlinx.coroutines.flow.Flow

interface FeatRepository {
    fun getEventsCreatedByUser(uId : Int): Flow<Result<List<Event>>>
    fun postEvent(): Flow<Result<String>>
}

