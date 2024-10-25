package com.debanshudatta.fintrack.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "indices",
    indices = [Index(value = ["sid"])]
)

data class IndicesEntity(
    @PrimaryKey
    val sid: String,
    val chartRange: List<String>,
    val lastClosePrice: Double,
    val name: String?,
    val type: List<String>,
    val inWatchList: Boolean = false
)
