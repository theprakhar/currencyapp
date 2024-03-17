package com.prakhar.currency.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey @ColumnInfo(name = "currencyCode") val currencyCode: String,
    @ColumnInfo(name = "exchangeRate") val exchangeRate: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Currency
        return currencyCode == other.currencyCode
    }

    override fun hashCode(): Int {
        var result = currencyCode.hashCode()
        result = 31 * result + exchangeRate.hashCode()
        return result
    }
}
