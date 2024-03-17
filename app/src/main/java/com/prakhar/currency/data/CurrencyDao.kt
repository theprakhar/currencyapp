package com.prakhar.currency.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.prakhar.currency.model.Attribute
import com.prakhar.currency.model.Currency

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrency(currency: Currency)

    @Transaction
    suspend fun upsertCurrencies(currencies: List<Currency>) {
        currencies.forEach { currency: Currency -> upsertCurrency(currency) }
    }

    @Query("SELECT * FROM currency ORDER BY currencyCode ASC")
    fun getAllCurrencies(): LiveData<MutableList<Currency>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAttribute(attribute: Attribute)

    @Query("SELECT * FROM attributes WHERE name=:attributeName LIMIT 1")
    fun getAttribute(attributeName: String): LiveData<Attribute?>

}