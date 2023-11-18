package com.darthkiler.newstestproject.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.darthkiler.newstestproject.navigation.NavigationArgs
import com.darthkiler.newstestproject.navigation.getArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(
        DetailsScreenUIState(
            isLoading = true
        ) {
            handleEvent(it)
        }
    )

    val uiState = _uiState.asStateFlow()

    init {
        handleArgument(savedStateHandle.getArgument())
    }

    private fun handleArgument(detailsScreenArgs: NavigationArgs.DetailsScreenArgs) {
        _uiState.update {
            it.copy(
                id = detailsScreenArgs.id,
                isLoading = false
            )
        }
    }

    private fun handleEvent(event: DetailsListScreenEvent) {
        when (event) {
            else -> {}
        }
    }

    sealed interface DetailsListScreenEvent {

    }
}