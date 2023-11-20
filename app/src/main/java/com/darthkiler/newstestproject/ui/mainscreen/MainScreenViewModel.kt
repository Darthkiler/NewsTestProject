package com.darthkiler.newstestproject.ui.mainscreen

import androidx.lifecycle.ViewModel
import com.darthkiler.newstestproject.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    navigationManager: NavigationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MainScreenUIState(
            navigationFlow = navigationManager.subscribeToNavigationFlow()
        )
    )

    val uiState = _uiState.asStateFlow()
}
