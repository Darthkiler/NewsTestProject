package com.darthkiler.newstestproject.domain.usecase

import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.domain.repository.ContentDomainRepository
import com.darthkiler.newstestproject.domain.utils.Status
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

interface GetContentByIdUseCase {
    suspend operator fun invoke(id: String): Status<ContentModel>
}

internal class GetContentByIdImpl @Inject constructor(
    private val contentDomainRepository: ContentDomainRepository
) : GetContentByIdUseCase {
    override suspend fun invoke(id: String): Status<ContentModel> {
        return contentDomainRepository.getContentDetailsById(id)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetContentByIdModule {

    @Binds
    internal abstract fun provideModule(
        getContentByIdImpl: GetContentByIdImpl
    ): GetContentByIdUseCase
}
