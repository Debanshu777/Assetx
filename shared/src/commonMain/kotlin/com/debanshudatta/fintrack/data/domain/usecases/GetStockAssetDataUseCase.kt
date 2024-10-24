package com.debanshudatta.fintrack.data.domain.usecases

import com.debanshudatta.fintrack.data.domain.database.entities.AssetEntity
import com.debanshudatta.fintrack.data.domain.database.entities.AssetType
import com.debanshudatta.fintrack.data.repository.LocalDataRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetStockAssetDataUseCase : KoinComponent {
    private val localDataRepository: LocalDataRepository by inject()
    fun getStockAssetData(assetType: AssetType): Flow<List<AssetEntity>> {
        return localDataRepository.getStockAssetData(assetType)
    }

    suspend fun upsertStockAsset(assetEntity: AssetEntity) {
        localDataRepository.upsertStockAsset(assetEntity)
    }
}
