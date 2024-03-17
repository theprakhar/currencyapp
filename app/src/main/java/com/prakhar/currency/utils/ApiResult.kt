package com.prakhar.currency.utils

import com.prakhar.currency.model.ApiResult
import retrofit2.Response


suspend fun <T> apiCallResult(call: suspend () -> Response<T>): ApiResult<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return ApiResult.Success(body)
            }
        }
    } catch (e: Exception) {
    }
    //generic message for now
    return ApiResult.Error("Something went wrong")
}

