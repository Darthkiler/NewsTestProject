package com.darthkiler.newstestproject.ui.mainscreen

import com.darthkiler.newstestproject.navigation.NavigationAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

data class MainScreenUIState(
    val navigationFlow: SharedFlow<NavigationAction>
)