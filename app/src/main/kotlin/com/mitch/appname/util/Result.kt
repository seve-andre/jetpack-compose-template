package com.mitch.appname.util

import com.mitch.appname.util.Result.Error
import com.mitch.appname.util.Result.Loading
import com.mitch.appname.util.Result.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * [Result] is useful for situations where an operation could go wrong.
 *
 * [Result] returns:
 * - [Success]: if nothing goes wrong
 * - [Error]: if something goes wrong
 * - [Loading]: if it's awaiting for the result
 *
 * @param T type of a successful operation
 */
sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    data object Loading : Result<Nothing>
}

/**
 * Converts a simple flow to a [Result] flow.
 *
 * @param T type of a successful operation
 * @return same flow wrapped in a [Result]
 */
fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Success(it)
        }
        .onStart { emit(Loading) }
        .catch { emit(Error(it)) }
}
