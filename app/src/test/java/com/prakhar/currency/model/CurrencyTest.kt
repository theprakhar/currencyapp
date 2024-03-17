package com.prakhar.currency.model

import org.junit.Test

class CurrencyTest {

    @Test
    fun `test same currency obj should be equal`() {
        val a = Currency("USD", 1.00)
        val b = a.copy()
        assert(a == b)
        val c = Currency("USD", 1.00)
        assert(a == c)
    }
    @Test
    fun `test same currency obj but different val should be equal`() {
        val a = Currency("USD", 1.00)
        val b = Currency("USD", 2.00)
        assert(a == b)
    }
    @Test
    fun `test different currency should not be equal`() {
        val a = Currency("USD", 1.00)
        val b = Currency("INR", 3.00)
        assert(a != b)
    }
    @Test
    fun `test different currency but same rate should not be equal`() {
        val a = Currency("USD", 1.00)
        val b = Currency("INR", 1.00)
        assert(a != b)
    }
}