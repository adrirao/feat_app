package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.Event
import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.request.RequestEvent
import kotlinx.coroutines.flow.Flow

interface FeatRepository {
    fun getEventsCreatedByUser(uId: Int): Flow<Result<List<Event>>>
    fun postEvent(req:RequestEvent): Flow<Result<String>>
    fun getEventsByUser(uId: Int): Flow<Result<List<Event>>>
    fun getPeriodicity(): Flow<Result<List<Periodicity>>>
    fun getEventsToday() : Flow<Result<List<Event>>>
}

