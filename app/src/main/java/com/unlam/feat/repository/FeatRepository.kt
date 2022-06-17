package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.*
import com.unlam.feat.model.request.*
import com.unlam.feat.model.response.ResponseDataSport
import com.unlam.feat.model.response.ResponseDetailEvent
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.unlam.feat.model.ResponseSearchEventDetail


interface FeatRepository {
    //<editor-fold desc="Events">
    fun getEventsToday(uId: String): Flow<Result<List<Event>>> // @GET("/events/")
    fun getEventsSuggestedForUser(uId: String): Flow<Result<List<Event>>> // @GET("/events/getAllEventSuggestedForUser/{uid}")
    fun getEventsCreatedByUser(uId: String): Flow<Result<List<Event>>> // @GET("/events/getAllCreatedByUser/{uid}")
    fun getEventsByOrganizer(organizer: Int): Flow<Result<List<Event>>> // @GET("/events/getAllByOrganizer/{organizer}")
    fun getEventsApplied(uId: String): Flow<Result<List<Event>>> // @GET("/events/getAllApplied/{uid}")
    fun getEventsConfirmed(uId: String): Flow<Result<List<Event>>> // @GET("/events/getAllConfirmed/{uid}")
    fun getEventsByUser(uId: String): Flow<Result<List<Event>>> // @GET("/events/getAllByUser/{uid}")
    fun getEventById(id: Int): Flow<Result<Event>> // @GET("/events/getEventById/{id}")
    fun postEvent(req: RequestEvent): Flow<Result<String>> // @POST("/events/create")

    //</editor-fold">
    //<editor-fold desc="Availabilities">
    fun getAvailabilities(): Flow<Result<List<Availability>>> // @GET("/availabilities/")
    fun getAvailability(id: Int): Flow<Result<Availability>> // @GET("/availabilities/{id}")
    fun createAvailability(req: RequestAvailability): Flow<Result<String>> // @POST("/availabilities/create")

    //</editor-fold desc="Availabilities">
    //<editor-fold desc="Levels">
    fun getLevels(): Flow<Result<List<Level>>> // @GET("/levels/")
    fun getLevel(id: Int): Flow<Result<Level>> // @GET("/levels/{id}")
    fun createLevel(req: RequestLevel): Flow<Result<String>> // @POST("/levels/create")

    //</editor-fold desc="Levels">
    //<editor-fold desc="Periodicities">
    fun getPeriodicities(): Flow<Result<List<Periodicity>>> // @GET("/periodicities/")
    fun getPeriodicity(id: Int): Flow<Result<Periodicity>> // @GET("/periodicities/{id}")
    fun getAllLevelsBySportGeneric(id: Int): Flow<Result<List<Level>>>// @GET("/levels/getAllBySportGeneric/{id}")
    fun createPeriodicity(req: RequestPeriodicity): Flow<Result<String>> // @GET("/periodicities/create")
    //</editor-fold desc="Periodicities">
    //<editor-fold desc="Players">
    fun getPlayers(): Flow<Result<List<Player>>> // @GET("/players/")
    fun getPlayer(id: Int): Flow<Result<Player>> // @GET("/players/{id}")
    fun getAllByPerson(personId: Int): Flow<Result<List<Player>>> // @GET("/players/getAllByPerson/{personId}")
    fun getAllPlayersSuggestedForEvent(eventId: Int): Flow<Result<List<Player>>> // @GET("/players/getAllPlayersSuggestedForEvent/{eventId}")
    fun getAllPlayersConfirmedByEvent(eventId: Int) : Flow<Result<List<Player>>> // @GET("/players/getAllConfirmedByEvent/{eventId}")
    fun getAllPlayersAppliedByEvent(eventId: Int) : Flow<Result<List<Player>>> // @GET("/players/getAllAppliedByEvent/{eventId}")
    fun createPlayer(req: RequestPlayer): Flow<Result<String>> // @POST("/players/create")

    //</editor-fold desc="Players">
    //<editor-fold desc="Positions">
    fun getPositions(): Flow<Result<List<Position>>> // @GET("/positions/")
    fun getPosition(id: Int): Flow<Result<Position>> // @GET("/positions/{id}")
    fun getAllPositionsBySportGeneric(id: Int): Flow<Result<List<Position>>> // @GET("/positions/getAllBySportGeneric/{id}")
    fun createPosition(req: RequestPosition): Flow<Result<String>> // @POST("/positions/create")

    //</editor-fold desc="Positions">
    //<editor-fold desc="Sports">
    fun getSports(): Flow<Result<List<Sport>>> // @GET("/sports/")
    fun getSport(id: Int): Flow<Result<Sport>> // @GET("/positions/{id}")

    //</editor-fold desc="Sports">
    //<editor-fold desc="Users">
    fun getUsers(): Flow<Result<List<User>>> // @GET("/users/")
    fun getUser(id: Int): Flow<Result<User>> // @GET("/users/{id}")
    fun createUser(req:RequestUser): Flow<Result<String>> // @POST("/users/create")
    //</editor-fold desc="Users">
    //<editor-fold desc="Persons">
    fun getPerson(uId: String): Flow<Result<Person>> // @GET("/persons/getPersonById/{id}")
    fun createPerson(req:RequestPerson): Flow<Result<String>> // @POST("/persons/create")
    fun updatePerson(req:RequestUpdatePerson): Flow<Result<String>> // @PUT("/persons/update")
    //</editor-fold desc="Persons">

    //<editor-fold desc="SportsGenerics">
    fun getGenericsSports(): Flow<Result<List<SportGeneric>>> // @GET("/sportsGeneric/")
    //</editor-fold desc="SportsGenerics">

    //<editor-fold desc="Valuations">
    fun getValuations(): Flow<Result<List<Valuation>>> // @GET("/valuations/")
    //</editor-fold desc="Valuations">

    //</editor-fold desc="Addresses">
    fun getAddress(personId:Int):  Flow<Result<Address>>  // @GET("/addresses/{id}")
    fun addAddress(req:RequestAddress): Flow<Result<String>> // @POST("/persons/create")
    //</editor-fold desc="Addresses">

    //</editor-fold desc="Multiple EndPoints">
    fun getDataDetailEvent(idEvent:Int): Flow<Result<ResponseDetailEvent>>
    fun getDataSportScreen(uId: String,sportGenericId:Int): Flow<Result<ResponseDataSport>>
    //</editor-fold desc="Multiple EndPoints">


}

