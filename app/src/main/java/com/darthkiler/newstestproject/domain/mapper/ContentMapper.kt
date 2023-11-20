package com.darthkiler.newstestproject.domain.mapper

import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.network.api.content.model.ContentDetailsResponseBody
import com.darthkiler.newstestproject.network.api.content.model.ContentResponseBody

internal fun ContentResponseBody.Response.Result.toContentModel() =
    ContentModel(
        id = this.id,
        title = this.webTitle,
        imageUrl = this.fields?.thumbnail
    )

internal fun ContentDetailsResponseBody.Response.toContentModel() =
    ContentModel(
        id = this.content?.id,
        imageUrl = this.content?.fields?.thumbnail,
        title = this.content?.webTitle
    )