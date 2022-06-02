package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.*
import com.unlam.feat.model.request.*
import com.unlam.feat.provider.FeatProvider
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
    //<editor-fold desc="Events">

    override fun getEventsToday(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsToday().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsSuggestedForUser(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsSuggestedForUser(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsCreatedByUser(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsCreatedByUser(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsByOrganizer(organizer: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsByOrganizer(organizer).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsApplied(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsApplied(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsConfirmed(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsConfirmed(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsByUser(uId: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsConfirmed(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventById(id: Int): Flow<Result<Event>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventById(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun postEvent(request: RequestEvent): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.postEvent(request).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    //</editor-fold desc="Events">
    //<editor-fold desc="Availabilities">

    override fun getAvailabilities(): Flow<Result<List<Availability>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAvailabilities().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAvailability(id: Int): Flow<Result<Availability>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAvailability(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))

        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createAvailability(req: RequestAvailability): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createAvailability(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Availabilities">
    //<editor-fold desc="Levels">

    override fun getLevels(): Flow<Result<List<Level>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getLevels().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getLevel(id: Int): Flow<Result<Level>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getLevel(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createLevel(req: RequestLevel): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createLevel(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Levels">
    //<editor-fold desc="Periodicities">

    override fun getPeriodicities(): Flow<Result<List<Periodicity>>> = flow {
        try {
            emit(Result.Loading())
            val periodicity = featProvider.getPeriodicities().body() ?: listOf()
            emit(Result.Success(data = periodicity))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPeriodicity(id: Int): Flow<Result<Periodicity>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPeriodicity(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPeriodicity(req: RequestPeriodicity): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPeriodicity(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Periodicities">
    //<editor-fold desc="Players">

    override fun getPlayers(): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayers().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPlayer(id: Int): Flow<Result<Player>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayer(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllByPerson(personId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllByPerson(personId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllPlayersSuggestedForEvent(eventId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPlayersSuggestedForEvent(eventId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPlayer(req: RequestPlayer): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPlayer(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    //</editor-fold desc="Players">
    //<editor-fold desc="Positions">

    override fun getPositions(): Flow<Result<List<Position>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPositions().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPosition(id: Int): Flow<Result<Position>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPosition(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPosition(req: RequestPosition): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPosition(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Positions">
    //<editor-fold desc="Sports">

    override fun getSports(): Flow<Result<List<Sport>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getSports().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getSport(id: Int): Flow<Result<Sport>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getSport(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Sports">
    //<editor-fold desc="Users">

    override fun getUsers(): Flow<Result<List<User>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getUsers().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getUser(id: Int): Flow<Result<User>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getUser(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(Result.Error(message = "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Users">
}