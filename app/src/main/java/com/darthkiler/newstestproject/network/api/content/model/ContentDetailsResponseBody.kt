package com.darthkiler.newstestproject.network.api.content.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailsResponseBody(
    @SerialName("response")
    val response: Response? // TODO возможно модель не всегда будет такой
) {
    @Serializable
    data class Response(
        @SerialName("status")
        val status: String?,
        @SerialName("userTier")
        val userTier: String?,
        @SerialName("total")
        val total: Int?,

        @SerialName("content")
        val content: Content?
    ) {

        @Serializable
        data class Content(
            @SerialName("id")
            val id: String?,
            @SerialName("type")
            val type: String?,
            @SerialName("sectionId")
            val sectionId: String?,
            @SerialName("sectionName")
            val sectionName: String?,
            @SerialName("webPublicationDate")
            val webPublicationDate: String?,
            @SerialName("webTitle")
            val webTitle: String?,
            @SerialName("webUrl")
            val webUrl: String?,
            @SerialName("apiUrl")
            val apiUrl: String?,
            @SerialName("fields")
            val fields: Field?,
            @SerialName("isHosted")
            val isHosted: Boolean?,
            @SerialName("pillarId")
            val pillarId: String?,
            @SerialName("pillarName")
            val pillarName: String?
        ) {

            @Serializable
            data class Field(
                @SerialName("thumbnail")
                val thumbnail: String?
            )
        }
    }
}
