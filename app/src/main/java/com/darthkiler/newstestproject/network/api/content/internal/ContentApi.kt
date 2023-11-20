package com.darthkiler.newstestproject.network.api.content.internal

import com.darthkiler.newstestproject.network.api.content.model.ContentDetailsResponseBody
import com.darthkiler.newstestproject.network.api.content.model.ContentResponseBody
import com.darthkiler.newstestproject.network.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url
import javax.inject.Singleton

interface ContentApi {

    @GET(Constants.CONTENT_SEARCH_PATH)
    suspend fun getContent(
        @QueryMap queryParams: Map<String, String>
    ): ContentResponseBody

    @GET
    suspend fun getContentById(
        @Url id: String,
        @QueryMap queryParams: Map<String, String>
    ): ContentDetailsResponseBody
}

@Module
@InstallIn(SingletonComponent::class)
internal class ContentApiModule {

    @Singleton
    @Provides
    internal fun provideModule(
        retrofit: Retrofit.Builder
    ) = retrofit.build().create(ContentApi::class.java)
}
