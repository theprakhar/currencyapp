package com.prakhar.currency.data

import android.util.Log
import com.prakhar.currency.BuildConfig
import com.prakhar.currency.enums.StateStatus
import com.prakhar.currency.model.Attribute
import com.prakhar.currency.model.LatestExchangeRateResponse
import com.prakhar.currency.model.ApiResult
import com.prakhar.currency.utils.apiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(
    private val currencyDao: CurrencyDao,
    private val openExchangeRateService: OpenExchangeRateService,
    private val preferences: Preferences
) {

    fun getAllCurrencies() = currencyDao.getAllCurrencies()

    suspend fun fetchLatestExchangeRates(): StateStatus {
        Log.d("Timestamp", preferences.canFetchLatestExchangeRates.toString())

        if (preferences.canFetchLatestExchangeRates) {
            val result = apiCallResult {
                openExchangeRateService.fetchLatestExchangeRates(BuildConfig.OPEN_EXCHANGE_APP_ID)
            }
            if (result is ApiResult.Success) {
                onLatestExchangeRateFetchSuccess(result)
                return StateStatus.SUCCESS
            }
            return StateStatus.FAILED
        }
        return StateStatus.SUCCESS
    }

    private suspend fun onLatestExchangeRateFetchSuccess(result: ApiResult.Success<LatestExchangeRateResponse>) {
        saveExchangeRatesInDb(result.data)
        preferences.lastExchangeRateFetchTimestamp = System.currentTimeMillis()
    }


    private suspend fun saveExchangeRatesInDb(body: LatestExchangeRateResponse?) {
        body?.currencies?.let {
            currencyDao.upsertCurrencies(
                it
            )
        }
    }

    fun getAttribute(name: String) = currencyDao.getAttribute(name)

    fun upsertAttribute(attribute: Attribute) {
        CoroutineScope(Dispatchers.IO).launch {
            currencyDao.upsertAttribute(attribute)
        }
    }


}