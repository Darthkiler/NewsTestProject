package com.darthkiler.newstestproject.domain.usecase

import androidx.paging.PagingData
import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.domain.repository.ContentDomainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetContentListUseCase {
    operator fun invoke(): Flow<PagingData<ContentModel>>
}

internal class GetContentListImpl @Inject constructor(
    private val contentDomainRepository: ContentDomainRepository
) : GetContentListUseCase {
    override fun invoke(): Flow<PagingData<ContentModel>> {
        return contentDomainRepository.getContentList()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetContentListModule {

    @Binds
    internal abstract fun provideModule(
        getContentListImpl: GetContentListImpl
    ): GetContentListUseCase
}
