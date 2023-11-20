package com.darthkiler.newstestproject.network.utils

import com.darthkiler.newstestproject.network.utils.Constants.CONTENT_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECT_TIMEOUT = 30L
private const val READ_TIMEOUT = 40L



@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor? {
        return HttpLoggingInterceptor().takeIf {
            true // TODO в дебажных билдах
        }?.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(
        interceptor: HttpLoggingInterceptor?,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(
                getJson().asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .client(OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).apply {
                    interceptor?.let {
                        addInterceptor(it)
                    }
                }.build()).apply {
                baseUrl(CONTENT_BASE_URL)
            }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getJson() = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}