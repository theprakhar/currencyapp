package com.prakhar.currency.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prakhar.currency.enums.StateStatus
import com.prakhar.currency.model.LatestExchangeRateResponse
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import io.mockk.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody


class RepositoryTest {
    private var context: Context
    private var currencyDao: CurrencyDao
    private var apiService: OpenExchangeRateService
    private var preferences: Preferences
    private var repository: Repository

    init {
        context = mockk<Context>(relaxed = true)
        currencyDao = mockk<CurrencyDao>(relaxed = true)
        apiService = mockk<OpenExchangeRateService>(relaxed = true)
        preferences = mockk<Preferences>(relaxed = true)
        repository = Repository(currencyDao, apiService, preferences)
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    @Throws(Exception::class)
    fun `when data is stale or empty,and api fetch is success`() = runBlocking {
        val spy = spyk(repository)
        val response = mockk<LatestExchangeRateResponse>(relaxed = true)
        coEvery { preferences.canFetchLatestExchangeRates } returns true
        coEvery { apiService.fetchLatestExchangeRates(any()) } returns Response.success(response)
        val status = spy.fetchLatestExchangeRates()
        assert(status == StateStatus.SUCCESS)
    }

    @Test
    @Throws(Exception::class)
    fun `when data is stale or empty,and api fetch is failed`() = runBlocking {
        val spy = spyk(repository)
        val body = "Some Error".toResponseBody("application/json".toMediaType())
        coEvery { preferences.canFetchLatestExchangeRates } returns true
        coEvery { apiService.fetchLatestExchangeRates(any()) } returns Response.error(400, body)
        val status = spy.fetchLatestExchangeRates()
        assert(status == StateStatus.FAILED)
    }

    @Test
    @Throws(Exception::class)
    fun `when data is not stale or empty`() = runBlocking {
        val spy = spyk(repository)
        val response = mockk<LatestExchangeRateResponse>(relaxed = true)
        coEvery { preferences.canFetchLatestExchangeRates } returns false
        coEvery { apiService.fetchLatestExchangeRates(any()) } returns Response.success(response)
        val status = spy.fetchLatestExchangeRates()
        assert(status == StateStatus.SUCCESS)
    }
}