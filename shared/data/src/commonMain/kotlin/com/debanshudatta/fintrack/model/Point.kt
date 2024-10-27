package com.debanshudatta.fintrack.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Point(
    @SerialName("price")
    val price: Double,
    @SerialName("ts")
    val ts: String?
)
