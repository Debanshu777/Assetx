package com.debanshudatta.fintrack.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stock",
    indices = [Index(value = ["sid", "ticker"])]
)
data class StockEntity(
    @PrimaryKey
    val sid: String,
    val change: Double = 0.0,
    val marketCap: String,
    val name: String,
    val price: Double,
    val sector: String,
    val slug: String,
    val ticker: String,
    val inWatchList: Boolean = false
)
