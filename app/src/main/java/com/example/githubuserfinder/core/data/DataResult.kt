package com.example.githubuserfinder.core.data

sealed class DataResult<out R>(
    open val value: R? = null,
    open val exception: Exception? = null,
) {
    class Success<T>(override val value: T) : DataResult<T>()
    class Error(override val exception: Exception) : DataResult<Nothing>()
}
