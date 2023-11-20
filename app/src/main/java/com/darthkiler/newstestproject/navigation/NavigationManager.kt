package com.darthkiler.newstestproject.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    private val navigationFLow = MutableSharedFlow<NavigationAction>(extraBufferCapacity = 1)
    fun subscribeToNavigationFlow() = navigationFLow.asSharedFlow()

    fun navigate(
        navigationAction: NavigationAction
    ) {
        navigationFLow.tryEmit(navigationAction)
    }
}
