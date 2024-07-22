package com.debanshudatta.fintrack.data.domain.model


import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Data(
    @EncodeDefault
    @SerialName("losers")
    val losers: List<Stock> = emptyList() ,

    @EncodeDefault
    @SerialName("gainers")
    val gainers: List<Stock> = emptyList(),

    @EncodeDefault
    @SerialName("active")
    val active: List<Stock> = emptyList(),

    @EncodeDefault
    @SerialName("approachingHigh")
    val approachingHigh: List<Stock> = emptyList(),

    @EncodeDefault
    @SerialName("approachingLow")
    val approachingLow: List<Stock> = emptyList()
)