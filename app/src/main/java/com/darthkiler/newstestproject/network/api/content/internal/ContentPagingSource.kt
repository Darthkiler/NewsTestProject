package com.darthkiler.newstestproject.network.api.content.internal

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.darthkiler.newstestproject.network.api.content.model.ContentResponseBody
import com.darthkiler.newstestproject.network.utils.Constants.API_KEY
import com.darthkiler.newstestproject.network.utils.Constants.FIELDS_PARAM
import com.darthkiler.newstestproject.network.utils.Constants.PAGE
import javax.inject.Inject

internal class ContentPagingSource @Inject constructor(
    private val contentApi: ContentApi
) : PagingSource<Int, ContentResponseBody.Response.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContentResponseBody.Response.Result> {
        return try {
            val currentPage = params.key ?: 1
            val results = contentApi.getContent(
                queryParams = mapOf(
                    FIELDS_PARAM to "thumbnail",
                    PAGE to currentPage.toString(),
                    API_KEY to "bab1fae0-d33d-4bfb-988e-8dfa0ba99d8f" // TODO плюсы
                )
            )
            LoadResult.Page(
                data = results.response?.results ?: emptyList(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = results.response?.currentPage?.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ContentResponseBody.Response.Result>): Int? {
        return state.anchorPosition
    }
}
