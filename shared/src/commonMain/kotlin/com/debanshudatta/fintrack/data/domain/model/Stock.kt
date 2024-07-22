package com.debanshudatta.fintrack.data.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    @SerialName("change")
    val change: Double,
    @SerialName("marketCap")
    val marketCap: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("sector")
    val sector: String,
    @SerialName("sid")
    val sid: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("ticker")
    val ticker: String
)