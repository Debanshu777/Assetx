package com.debanshudatta.fintrack.data.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("data")
    val `data`: Data,
    @SerialName("success")
    val success: Boolean
)
