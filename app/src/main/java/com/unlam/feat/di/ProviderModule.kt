package com.unlam.feat.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unlam.feat.common.Constants
import com.unlam.feat.provider.FeatProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton

//val nullOnEmptyConverterFactory = object : Converter.Factory() {
//    fun converterFactory() = this
//    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
//        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
//        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
//    }
//}
@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = Constants.FEAT_URL_BASE.toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {

        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ) = Converter<ResponseBody, Any?> {
                if(it.contentLength() != 0L) retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations).convert(it) else null
            }
        }

        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()


    }



    @Provides
    @Singleton
    fun providerFeatProvider(retrofit: Retrofit): FeatProvider =
        retrofit.create(FeatProvider::class.java)

    @Provides
    @Singleton
    fun provideFirebaseInstance() = Firebase.auth
}

