package com.prakhar.currency.data

import android.content.Context

class Preferences(context: Context) {
    private val sharedPrefs = context.getSharedPreferences(
        "${context.packageName}.properties",
        Context.MODE_PRIVATE
    )
    private val editor = sharedPrefs.edit()

    var lastExchangeRateFetchTimestamp: Long
        get() = (sharedPrefs.getLong(TIMESTAMP, NOT_AVAILABLE))
        set(value) = editor.putLong(TIMESTAMP, value).apply()

    val canFetchLatestExchangeRates
        get() = (timeSinceLastFetch == NOT_AVAILABLE || timeSinceLastFetch > THIRTY_MINUTES_IN_MILLS)


    private val timeSinceLastFetch: Long
        get() {

            return if (lastExchangeRateFetchTimestamp != NOT_AVAILABLE) {
                System.currentTimeMillis() - lastExchangeRateFetchTimestamp
            } else {
                NOT_AVAILABLE
            }
        }

    companion object {
        const val TIMESTAMP = "timestamp"
        const val THIRTY_MINUTES_IN_MILLS = 1_800_000L
        const val NOT_AVAILABLE = 0L
    }
}