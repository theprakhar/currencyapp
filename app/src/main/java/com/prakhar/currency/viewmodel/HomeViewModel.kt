package com.prakhar.currency.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.prakhar.currency.data.CurrencyDatabase
import com.prakhar.currency.data.OpenExchangeRateService
import com.prakhar.currency.data.Preferences
import com.prakhar.currency.data.Repository
import com.prakhar.currency.model.Attribute

class HomeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    companion object {
        private const val SELECTED_CURRENCY_ATTR = "SELECTED_CURRENCY"
    }

    init {
        val currencyDao = CurrencyDatabase.getInstance(application.applicationContext).currencyDao()
        val openExchangeService = OpenExchangeRateService.getInstance()
        val preferences = Preferences(application.applicationContext)
        repository = Repository(currencyDao, openExchangeService, preferences)
    }

    val currencies = repository.getAllCurrencies()

    suspend fun startFetchingCurrencies() {
        repository.fetchLatestExchangeRates()
    }

    fun setSelectedCurrency(currencyKey: String) {
        repository.upsertAttribute(Attribute(SELECTED_CURRENCY_ATTR, currencyKey))
    }

    val selectedCurrency = repository.getAttribute(SELECTED_CURRENCY_ATTR)

}