package com.debanshudatta.fintrack.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
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
    val ticker: String
)
