package com.unlam.feat.repository

import com.unlam.feat.common.Result
import com.unlam.feat.model.Event
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


}