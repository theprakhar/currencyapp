package com.prakhar.currency.utils

import org.junit.Test

class ConversionTest {
    private val exchangeRates = mapOf(
        "AED" to 3.6729,
        "CAD" to 1.35275,
        "EUR" to 0.913784,
        "GBP" to 0.777726,
        "INR" to 82.752346,
        "JEP" to 0.777726,
        "USD" to 1.0,
    )


    @Test
    fun `test USD to EUR conversion`() {
        val fromCurrency: Double = exchangeRates["USD"]!!
        val toCurrency: Double = exchangeRates["EUR"]!!
        val amount = 100.00
        val expectedValue = "91.38"
        val output = getConvertedText(amount, fromCurrency, toCurrency)
        assert(output == expectedValue)
    }
    @Test
    fun `test EUR to INR conversion`() {
        val fromCurrency: Double = exchangeRates["EUR"]!!
        val toCurrency: Double = exchangeRates["INR"]!!
        val amount = 100.00
        val expectedValue = "9056.01"
        val output = getConvertedText(amount, fromCurrency, toCurrency)
        assert(output == expectedValue)
    }
    @Test
    fun `test AED to JEP conversion`() {
        val fromCurrency: Double = exchangeRates["AED"]!!
        val toCurrency: Double = exchangeRates["JEP"]!!
        val amount = 50.00
        val expectedValue = "10.59"
        val output = getConvertedText(amount, fromCurrency, toCurrency)
        assert(output == expectedValue)
    }
    @Test
    fun `test GBP to CAD conversion`() {
        val fromCurrency: Double = exchangeRates["GBP"]!!
        val toCurrency: Double = exchangeRates["CAD"]!!
        val amount = 50.00
        val expectedValue = "86.97"
        val output = getConvertedText(amount, fromCurrency, toCurrency)
        assert(output == expectedValue)
    }

}
