package com.prakhar.currency.utils

import com.prakhar.currency.model.ApiResult
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class ResultTest {

    @Test
    fun `test when api call is success`() = runBlocking {
        val call: suspend () -> Response<Any> = {
            Response.success("Success")
        }
        val result = apiCallResult {
            call()
        }
        assert(result is ApiResult.Success)
    }

    @Test
    fun `test when api call is failed`() = runBlocking {
        val call: suspend () -> Response<Any> = {
            val body = "Some Error".toResponseBody("application/json".toMediaType())
            Response.error(400, body)
        }
        val result = apiCallResult {
            call()
        }
        assert(result is ApiResult.Error)
    }
}