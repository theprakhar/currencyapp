package com.prakhar.currency.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prakhar.currency.model.Attribute
import com.prakhar.currency.model.Currency

@Database(entities = [Currency::class, Attribute::class], version = 2, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        private const val DB_NAME = "currency.db"

        @Volatile
        private var instance: CurrencyDatabase? = null
        fun getInstance(context: Context): CurrencyDatabase {
            if (instance != null) {
                return instance as CurrencyDatabase
            }
            instance = buildDb(context)
            return instance as CurrencyDatabase
        }

        private fun buildDb(context: Context): CurrencyDatabase {
            return Room.databaseBuilder(
                context,
                CurrencyDatabase::class.java, DB_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}