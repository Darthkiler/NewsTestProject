package com.darthkiler.newstestproject.ui.screens.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.darthkiler.newstestproject.domain.usecase.GetContentListUseCase
import com.darthkiler.newstestproject.navigation.NavigationAction
import com.darthkiler.newstestproject.navigation.NavigationArgs
import com.darthkiler.newstestproject.navigation.NavigationDestination
import com.darthkiler.newstestproject.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainListScreenViewModel @Inject constructor(
    private val getContentListUseCase: GetContentListUseCase,
    private val navigationManager: NavigationManager
): ViewModel() {

    private val _resultsListState = getContentListUseCase().cachedIn(viewModelScope)
    val resultsListState get() = _resultsListState

    private val _uiState = MutableStateFlow(
        MainListScreenUIState() {
            handleEvent(it)
        }
    )
    val uiState get() = _uiState.asStateFlow()

    private fun handleEvent(event: MainListScreenEvent) {
        when (event) {
            is MainListScreenEvent.OnItemClick -> {
                handleOnItemClick(event.id)
            }
        }
    }

    private fun handleOnItemClick(id: String) {
        navigationManager.navigate(
            navigationAction = NavigationAction.NavigateToDestinationAction(
                destination = NavigationDestination.DetailsScreenDestination.withArg(
                    argument = NavigationArgs.DetailsScreenArgs.getDetailsScreenArgs(id)
                )
            )
        )
    }

    sealed interface MainListScreenEvent {
        data class OnItemClick(val id: String) : MainListScreenEvent
    }
}