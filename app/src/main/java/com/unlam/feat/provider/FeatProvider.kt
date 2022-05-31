package com.unlam.feat.provider

import com.unlam.feat.model.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeatProvider {
    @GET("/events/getAllCreatedByUser/{uid}")
    suspend fun getEventsCreatedByUser(@Path("uid") uid: Int): Response<List<Event>>

    @GET("/events/getAllByOrganizer/{organizer}")
    suspend fun getEventsByOrganizer(@Path("organizer") organizer: Int)

    @GET("/events/getAllApplied/{uid}")
    suspend fun getEventsApplied(@Path("uid") uid: Int)

    @GET("/events/getAllConfirmed/{uid}")
    suspend fun getEventsConfirmed(@Path("uid") uid: Int)

    @GET("/events/getAllByUser/{uid}")
    suspend fun getEventsByUser(@Path("uid") uid: Int)

    @GET("/events/getEventById/{id}")
    suspend fun getEventById(@Path("id") id: Int)

    @POST("/events/create")
    suspend fun postEvent()
}