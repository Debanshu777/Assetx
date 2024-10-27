package com.debanshudatta.fintrack.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Indices(
    @SerialName("sid")
    val sid: String,
    @SerialName("chartRange")
    val chartRange: List<String>,
    @SerialName("lastClosePrice")
    val lastClosePrice: Double,
    @SerialName("name")
    val name: String?,
    @SerialName("points")
    val points: List<Point>,
    @SerialName("type")
    val type: List<String>,
)
