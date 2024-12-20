package com.debanshudatta.fintrack.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndicesResponse(
    @SerialName("data")
    val `data`: List<Indices>,
    @SerialName("success")
    val success: Boolean?
)
