package com.darthkiler.newstestproject.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateWithNavAction(navAction: NavigationAction.NavigateToDestinationAction) {
    navigate(navAction.destination.destination) {
        navAction.popUpTo?.let {
            popUpTo(it) {
                inclusive = navAction.inclusive
            }
        }
    }
}

sealed class NavigationAction {

    data class NavigateToDestinationAction(
        val destination: NavigationDestination,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false,
        val popUpTo: String? = null
    ) : NavigationAction()

    data object PopBackStack : NavigationAction()
}