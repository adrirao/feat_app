package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.Event
import com.unlam.feat.model.Periodicity
import com.unlam.feat.model.request.RequestEvent
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
    override fun postEvent(request:RequestEvent): Flow<Result<String>> = flow{
            try {
                emit(Result.Loading())
                val response = featProvider.postEvent(request).code()
                if(response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(Result.Error("Algo malo ocurrio."))
            }catch (e :Exception){
                emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
            }
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

    override fun getPeriodicity(): Flow<Result<List<Periodicity>>>  = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val periodicity = featProvider.getPeriodicity().body() ?: listOf()
            emit(Result.Success(data = periodicity))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsToday(): Flow<Result<List<Event>>> = flow{
        try {
            emit(Result.Loading())
            delay(600)
            val events = featProvider.getEventsToday().body() ?: listOf()
            emit(Result.Success(data = events))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }


}