package com.darthkiler.newstestproject.domain.mapper

import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.network.api.content.model.ContentDetailsResponseBody
import com.darthkiler.newstestproject.network.api.content.model.ContentResponseBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun ContentResponseBody.Response.Result.toContentModel() =
    ContentModel(
        id = this.id,
        title = this.webTitle,
        imageUrl = this.fields?.thumbnail,
        category = this.sectionName,
        date = this.webPublicationDate?.mapStringDataToStringData(),
        link = this.webUrl,
        pillar = this.pillarName
    )

internal fun ContentDetailsResponseBody.Response.toContentModel() =
    ContentModel(
        id = this.content?.id,
        imageUrl = this.content?.fields?.thumbnail,
        title = this.content?.webTitle,
        category = this.content?.sectionName,
        date = this.content?.webPublicationDate?.mapStringDataToStringData(),
        link = this.content?.webUrl,
        pillar = this.content?.pillarName
    )

// TODO сделать както по другому
fun String.mapStringDataToStringData(): String? {
    return try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            .parse(this)?.time?.let {
                SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(
                    Date(it)
                )
            }
    } catch (e: Exception) {
        null
    }
}
