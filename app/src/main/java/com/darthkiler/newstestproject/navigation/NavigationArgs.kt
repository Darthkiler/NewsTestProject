package com.darthkiler.newstestproject.navigation

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Base64

@Serializable
sealed class NavigationArgs {

    @Serializable
    data class DetailsScreenArgs(
        private val id: String
    ): NavigationArgs() {
        private constructor() : this ("")

        fun getId(): String = String(Base64.getDecoder().decode(id))

        companion object {
            fun getDetailsScreenArgs(id: String): DetailsScreenArgs {
                return DetailsScreenArgs(
                    id = Base64.getEncoder().encodeToString(id.toByteArray())
                )
            }
        }
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
