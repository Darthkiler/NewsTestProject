package com.darthkiler.newstestproject.domain.utils

sealed interface Status<out T> {
    data class Success<T>(val data: T) : Status<T>
    data class Error(val error: Exception) : Status<Nothing>

    companion object {
        fun onStatusError() = Error(Exception())
        fun onSuccess() = Success(Any())
    }
}
