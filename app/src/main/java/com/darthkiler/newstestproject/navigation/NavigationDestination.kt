package com.darthkiler.newstestproject.navigation

interface NavigationDestination {
    val destination: String

    object MainListScreenDestination : NavigationRoute.Route("mainListScreenDestination")

    data object DetailsScreenDestination :
        NavigationRoute.RouteWithArgs<NavigationArgs.DetailsScreenArgs>(
            destinationPrefix = "detailsScreenDestination",
            argName = "detailsScreenArgs"
        )
}

sealed interface NavigationRoute {
    val route: String

    sealed class Route(final override val destination: String) : NavigationRoute, NavigationDestination {
        override val route: String = destination
    }

    sealed class RouteWithArgs<V : NavigationArgs>(
        val destinationPrefix: String,
        val argName: String
    ) : NavigationRoute {
        override val route = "$destinationPrefix/{$argName}"
        inline fun <reified T : V> withArg(
            argument: T
        ) = object : NavigationDestination {
            override val destination: String =
                "${this@RouteWithArgs.destinationPrefix}/${argument.toStringArg()}"
        }
    }
}
