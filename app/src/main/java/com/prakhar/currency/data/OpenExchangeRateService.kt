package com.prakhar.currency.data

import com.prakhar.currency.model.LatestExchangeRateResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeRateService {
    companion object{
        private const val BASE_URL = "https://openexchangerates.org/"
        private lateinit var  openExchangeRateService: OpenExchangeRateService
        fun getInstance() : OpenExchangeRateService {
            if (!::openExchangeRateService.isInitialized) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                openExchangeRateService = retrofit.create(OpenExchangeRateService::class.java)
            }

            return openExchangeRateService
        }
    }
    @GET("api/latest.json")
    suspend fun fetchLatestExchangeRates(@Query("app_id") appId:String): Response<LatestExchangeRateResponse>




}