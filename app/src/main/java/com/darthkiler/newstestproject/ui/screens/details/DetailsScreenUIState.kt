package com.darthkiler.newstestproject.ui.screens.details

import com.darthkiler.newstestproject.domain.model.ContentModel

data class DetailsScreenUIState(
    val isLoading: Boolean = false,
    val contentModel: ContentModel? = null,
    val id: String? = null,

    val onEvent: (DetailsScreenViewModel.DetailsListScreenEvent) -> Unit
)