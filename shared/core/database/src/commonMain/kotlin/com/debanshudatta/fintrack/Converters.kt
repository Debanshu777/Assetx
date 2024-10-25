package com.debanshudatta.fintrack

import androidx.room.TypeConverter
import com.debanshudatta.fintrack.entities.Transaction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromTransactionList(value: List<Transaction>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toTransactionList(value: String): List<Transaction> {
        return Json.decodeFromString(value)
    }
}
