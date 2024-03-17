package com.prakhar.currency.utils

fun getConvertedText(amount: Double, fromCurrencyRate: Double, toCurrencyRate: Double): String {
    val value = (toCurrencyRate / fromCurrencyRate) * amount
    return String.format("%.2f", value)
}