package com.darthkiler.newstestproject.network.api.content.internal

import com.darthkiler.newstestproject.domain.utils.Status
import com.darthkiler.newstestproject.network.api.content.model.ContentDetailsResponseBody
import com.darthkiler.newstestproject.network.utils.Constants
import javax.inject.Inject


class ContentRequestHandler @Inject constructor(
    private val contentApi: ContentApi
) {

    internal suspend fun getContentDetailsById(id: String): Status<ContentDetailsResponseBody> {
        return try {
            Status.Success(
                contentApi.getContentById(
                    id = id,
                    queryParams = mapOf(
                        Constants.FIELDS_PARAM to "thumbnail",
                        Constants.API_KEY to "bab1fae0-d33d-4bfb-988e-8dfa0ba99d8f" // TODO плюсы
                    )
                )
            )
        } catch (e: Exception) {
            Status.Error(e)
        }
    }
}