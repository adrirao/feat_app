package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.*
import com.unlam.feat.model.request.*
import com.unlam.feat.provider.FeatProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatRepositoryImp
@Inject
constructor(
    private val featProvider: FeatProvider
) : FeatRepository {
    override fun getEventsToday(uId: Int): Flow<Result<List<Event>>> {
        TODO("Not yet implemented")
    }

    override fun getEventsSuggestedForUser(uId: Int): Flow<Result<List<Event>>> {
        TODO("Not yet implemented")
    }

    override  fun getEventsCreatedByUser(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val events = featProvider.getEventsCreatedByUser(uId).body() ?: listOf()
            emit(Result.Success(data = events))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsByOrganizer(organizer: Int): Flow<Result<List<Event>>> {
        TODO("Not yet implemented")
    }

    override fun getEventsApplied(uId: Int): Flow<Result<List<Event>>> {
        TODO("Not yet implemented")
    }

    override fun getEventsConfirmed(uId: Int): Flow<Result<List<Event>>> {
        TODO("Not yet implemented")
    }

    override fun postEvent(request:RequestEvent): Flow<Result<String>> = flow{
            try {
                emit(Result.Loading())
                val response = featProvider.postEvent(request).code()
                if(response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(Result.Error("Algo malo ocurrio."))
            }catch (e :Exception){
                emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
            }
    }

    override fun getAvailabilities(): Flow<Result<List<Availability>>> {
        TODO("Not yet implemented")
    }

    override fun getAvailability(id: Int): Flow<Result<Availability>> {
        TODO("Not yet implemented")
    }

    override fun createAvailability(req: RequestAvailability): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    override fun getLevels(): Flow<Result<List<Level>>> {
        TODO("Not yet implemented")
    }

    override fun getLevel(id: Int): Flow<Result<Level>> {
        TODO("Not yet implemented")
    }

    override fun createLevel(req: RequestLevel): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    override fun getEventsByUser(uId: Int): Flow<Result<List<Event>>>  = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val events = featProvider.getEventsConfirmed(uId).body() ?: listOf()
            emit(Result.Success(data = events))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventById(id: Int): Flow<Result<Event>> {
        TODO("Not yet implemented")
    }

    override fun getPeriodicities(): Flow<Result<List<Periodicity>>>  = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val periodicity = featProvider.getPeriodicities().body() ?: listOf()
            emit(Result.Success(data = periodicity))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPeriodicity(id: Int): Flow<Result<Periodicity>> {
        TODO("Not yet implemented")
    }

    override fun createPeriodicity(req: RequestPeriodicity): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    override fun getPlayers(): Flow<Result<List<Player>>> {
        TODO("Not yet implemented")
    }

    override fun getPlayer(id: Int): Flow<Result<Player>> {
        TODO("Not yet implemented")
    }

    override fun getAllByPerson(personId: Int): Flow<Result<Player>> {
        TODO("Not yet implemented")
    }

    override fun getAllPlayersSuggestedForEvent(eventId: Int): Flow<Result<Player>> {
        TODO("Not yet implemented")
    }

    override fun createPlayer(req: RequestPlayer): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    override fun getPositions(): Flow<Result<List<Position>>> {
        TODO("Not yet implemented")
    }

    override fun getPosition(id: Int): Flow<Result<Position>> {
        TODO("Not yet implemented")
    }

    override fun createPosition(req: RequestPosition): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    override fun getSports(): Flow<Result<List<Sport>>> {
        TODO("Not yet implemented")
    }

    override fun getSport(id: Int): Flow<Result<Sport>> {
        TODO("Not yet implemented")
    }

    override fun getUsers(): Flow<Result<List<User>>> {
        TODO("Not yet implemented")
    }

    override fun getUser(id: Int): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

//    override fun getEventsToday(): Flow<Result<List<Event>>> = flow{
//        try {
//            emit(Result.Loading())
//            delay(600)
//            val events = featProvider.getEventsToday().body() ?: listOf()
//            emit(Result.Success(data = events))
//        } catch (e: Exception) {
//            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
//        }
//    }


}