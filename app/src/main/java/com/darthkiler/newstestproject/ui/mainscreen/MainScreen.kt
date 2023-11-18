package com.darthkiler.newstestproject.ui.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darthkiler.newstestproject.navigation.NavigationAction
import com.darthkiler.newstestproject.navigation.NavigationDestination
import com.darthkiler.newstestproject.navigation.navigateWithNavAction
import com.darthkiler.newstestproject.ui.screens.details.DetailsScreen
import com.darthkiler.newstestproject.ui.screens.mainlist.MainListScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        MainScreenNavigation(
            navHostController = navController
        )
    }

    LaunchedEffect(Unit) {
        uiState.navigationFlow.onEach { navAction ->
            when (navAction) {
                is NavigationAction.NavigateToDestinationAction -> {
                    navController.navigateWithNavAction(navAction)
                }

                is NavigationAction.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }.launchIn(this)
    }
}

@Composable
private fun MainScreenNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavigationDestination.MainListScreenDestination.route
    ) {
        composable(
            route = NavigationDestination.MainListScreenDestination.route
        ) {
            MainListScreen()
        }
        composable(
            route = NavigationDestination.DetailsScreenDestination.route
        ) {
            DetailsScreen()
        }
    }
}