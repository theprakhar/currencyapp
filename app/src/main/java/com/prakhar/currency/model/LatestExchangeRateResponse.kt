package com.prakhar.currency.model

import com.google.gson.annotations.SerializedName

data class LatestExchangeRateResponse(
    @SerializedName("timestamp") var timestamp: Long = 0,
    @SerializedName("rates") var exchangeRates: HashMap<String, Double>? = null
) {
    val currencies: List<Currency>
        get() {
            val list = mutableListOf<Currency>()
            exchangeRates?.forEach {
                list.add(Currency(it.key, it.value))
            }
            return list
        }
}
