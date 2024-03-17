package com.prakhar.currency.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
for storing misc attributes related to currency
* */
@Entity(tableName = "attributes")
data class Attribute(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: String
)

