package com.darthkiler.newstestproject.navigation

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
sealed class NavigationArgs {

    @Serializable
    data class DetailsScreenArgs(
        val id: Int
    ): NavigationArgs() {
        private constructor() : this (-1)
    }

    var argName: String = when (this) {
        is DetailsScreenArgs -> NavigationDestination.DetailsScreenDestination.argName
    }
}

inline fun<reified V : NavigationArgs> V.toStringArg(): String = Json.encodeToString(this)

inline fun <reified V : NavigationArgs> SavedStateHandle.getArgument(): V {
    return try {
        Json.decodeFromString(
            this.get<String>(V::class.java.getDeclaredConstructor().newInstance().argName).orEmpty()
        ) as V
    } catch (e: Exception) {
        throw Exception("Error, most likely you used the wrong type of argument or forgot to specify serialization! ")
    }
}
