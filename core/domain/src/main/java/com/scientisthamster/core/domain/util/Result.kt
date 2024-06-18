package com.scientisthamster.core.domain.util

sealed interface Result<out D, out E : Error> {

    data class Success<out D>(val data: D) : Result<D, Nothing>

    data class Error<out E : com.scientisthamster.core.domain.util.Error>(val error: E) :
        Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(transform(data))
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    return map { }
}

typealias EmptyDataResult<E> = Result<Unit, E>