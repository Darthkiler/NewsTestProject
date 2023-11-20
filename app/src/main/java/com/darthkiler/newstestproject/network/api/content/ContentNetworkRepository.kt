package com.darthkiler.newstestproject.network.api.content

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.darthkiler.newstestproject.domain.utils.Status
import com.darthkiler.newstestproject.network.api.content.internal.ContentPagingSource
import com.darthkiler.newstestproject.network.api.content.internal.ContentRequestHandler
import com.darthkiler.newstestproject.network.api.content.model.ContentDetailsResponseBody
import com.darthkiler.newstestproject.network.api.content.model.ContentResponseBody
import com.darthkiler.newstestproject.network.utils.Constants.PAGES_COUNT
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ContentNetworkRepository {
    fun getContent(): Flow<PagingData<ContentResponseBody.Response.Result>>

    suspend fun getContentDetailsById(id: String): Status<ContentDetailsResponseBody>
}

internal class ContentNetworkRepositoryImpl @Inject constructor(
    private val contentPagingSource: ContentPagingSource,
    private val contentRequestHandler: ContentRequestHandler
) : ContentNetworkRepository {
    override fun getContent(): Flow<PagingData<ContentResponseBody.Response.Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGES_COUNT // TODO сделать через запрос
            ),
            pagingSourceFactory = {
                contentPagingSource
            }
        ).flow
    }

    override suspend fun getContentDetailsById(id: String): Status<ContentDetailsResponseBody> {
        return contentRequestHandler.getContentDetailsById(id)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ContentNetworkRepositoryModule {

    @Binds
    internal abstract fun provideModule(
        contentNetworkRepositoryImpl: ContentNetworkRepositoryImpl
    ): ContentNetworkRepository
}
