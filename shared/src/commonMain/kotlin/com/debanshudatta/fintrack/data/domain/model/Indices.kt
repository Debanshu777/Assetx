package com.debanshudatta.fintrack.data.domain.model


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    indices = [Index(value = ["sid"])]
)
@Serializable
data class Indices(
    @PrimaryKey
    @SerialName("sid")
    val sid: String, //
    @SerialName("chartRange")
    val chartRange: List<String>, //
    @SerialName("lastClosePrice")
    val lastClosePrice: Double,
    @SerialName("name")
    val name: String?,
    @SerialName("points")
    val points: List<Point>, //
    @SerialName("type")
    val type: List<String>, //
    @SerialName("inUserPortfolio")
    val inWatchList: Boolean = false
)
