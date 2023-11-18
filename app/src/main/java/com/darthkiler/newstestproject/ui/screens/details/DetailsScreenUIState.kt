package com.darthkiler.newstestproject.ui.screens.details

data class DetailsScreenUIState(
    val isLoading: Boolean = false,
    val id: Int? = null,

    val onEvent: (DetailsScreenViewModel.DetailsListScreenEvent) -> Unit
)