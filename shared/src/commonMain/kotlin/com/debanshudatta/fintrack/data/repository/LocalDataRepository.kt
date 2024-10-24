package com.debanshudatta.fintrack.data.repository

import com.debanshudatta.fintrack.data.domain.database.LocalDatabase
import com.debanshudatta.fintrack.data.domain.database.entities.AssetEntity
import com.debanshudatta.fintrack.data.domain.database.entities.AssetType
import kotlinx.coroutines.flow.Flow

internal class LocalDataRepository(private val localDatabase: LocalDatabase) {
    fun getStockAssetData(assetType: AssetType): Flow<List<AssetEntity>> {
        return if (assetType != AssetType.ALL) {
            localDatabase.localDao().getAssetsByType(assetType)
        } else {
            localDatabase.localDao().getAllAssets()
        }
    }

    suspend fun upsertStockAsset(assetEntity: AssetEntity) {
        try {
            val data = localDatabase.localDao().upsertAsset(assetEntity)
            println("Data inserted: $data")
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
