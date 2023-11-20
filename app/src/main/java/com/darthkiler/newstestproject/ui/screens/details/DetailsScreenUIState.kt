package com.darthkiler.newstestproject.ui.screens.details

import com.darthkiler.newstestproject.domain.model.ContentModel

data class DetailsScreenUIState(
    val contentModel: ContentModel? = null,

    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val onEvent: (DetailsScreenViewModel.DetailsListScreenEvent) -> Unit
)
