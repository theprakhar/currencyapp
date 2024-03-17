package com.prakhar.currency.model


sealed class ApiResult<out T> {
    data class Error<T>(val message: String) : ApiResult<T>()
    data class Success<T>(val data: T) : ApiResult<T>()
}