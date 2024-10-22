package com.debanshudatta.fintrack.data.domain.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    indices = [Index(value = ["sid", "ticker"])]
)
@Serializable
data class Stock(
    @PrimaryKey
    @SerialName("sid")
    val sid: String,
    @SerialName("change")
    val change: Double = 0.0,
    @SerialName("marketCap")
    val marketCap: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("sector")
    val sector: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("ticker")
    val ticker: String,
    @SerialName("inUserPortfolio")
    val inUserPortfolio: Boolean = false
)