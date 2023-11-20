package com.darthkiler.newstestproject.domain.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.darthkiler.newstestproject.domain.mapper.toContentModel
import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.domain.utils.Status
import com.darthkiler.newstestproject.network.api.content.ContentNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ContentDomainRepository {
    fun getContentList(): Flow<PagingData<ContentModel>>

    suspend fun getContentDetailsById(id: String): Status<ContentModel>
}

internal class ContentDomainRepositoryImpl @Inject constructor(
    private val contentNetworkRepository: ContentNetworkRepository
) : ContentDomainRepository {
    override fun getContentList(): Flow<PagingData<ContentModel>> {
        return contentNetworkRepository.getContent().map {
            it.map {
                it.toContentModel()
            }
        }
    }

    override suspend fun getContentDetailsById(id: String): Status<ContentModel> {
        return contentNetworkRepository.getContentDetailsById(
            id = id
        ).let {
            when (it) {
                is Status.Error -> it
                is Status.Success -> {
                    if (it.data.response != null) {
                        Status.Success(
                            it.data.response.toContentModel()
                        )
                    } else {
                        Status.onStatusError()
                    }
                }
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ContentDomainRepositoryModule {

    @Binds
    internal abstract fun provideModule(
        contentDomainRepositoryImpl: ContentDomainRepositoryImpl
    ): ContentDomainRepository
}
