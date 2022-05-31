package com.unlam.feat

import com.unlam.feat.provider.FeatProvider
import com.unlam.feat.repository.FeatRepositoryImp
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets


class FeatRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val featProvider = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FeatProvider::class.java)

    private val featRepository = FeatRepositoryImp(featProvider)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Respuesta correcta de la llamada de Eventos creados por el usuario`() {
        mockWebServer.enqueueResponse("events_getAllCreatedByUser.json")

        runBlocking {
            val events = featRepository.getEventsCreatedByUser(1)
//            assertEquals(2, events[0].id)
        }
    }
}

fun MockWebServer.enqueueResponse(filePath: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}