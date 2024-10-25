package com.debanshudatta.fintrack.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "asset")
data class AssetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val mappingId: String,
    val type: AssetType,
    val marketValue: Double,
    val averagePurchasedValue: Double,
    val quantity: Int,
    val transactions: List<Transaction>
)

@Serializable
data class Transaction(
    val purchasedValue: Double,
    val timeInEpoch: Long,
    val quantity: Double,
)

enum class AssetType {
    STOCK,
    MUTUAL_FUND,
    ALL,
}
