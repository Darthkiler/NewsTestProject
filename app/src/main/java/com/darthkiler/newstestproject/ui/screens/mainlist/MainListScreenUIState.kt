package com.darthkiler.newstestproject.ui.screens.mainlist

data class MainListScreenUIState(
    val isLoading: Boolean = false,

    val onEvent: (MainListScreenViewModel.MainListScreenEvent) -> Unit
)
