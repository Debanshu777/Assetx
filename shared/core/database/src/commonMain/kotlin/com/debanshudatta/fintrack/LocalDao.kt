package com.debanshudatta.fintrack

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.debanshudatta.fintrack.entities.AssetEntity
import com.debanshudatta.fintrack.entities.AssetType
import com.debanshudatta.fintrack.entities.IndicesEntity
import com.debanshudatta.fintrack.entities.StockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    // Stocks
    @Transaction
    @Query("SELECT * FROM Stock WHERE sid = :sid")
    suspend fun getStockById(sid: String): StockEntity

    @Transaction
    @Query("DELETE FROM Stock WHERE sid = :sid")
    suspend fun deleteStockById(sid: String)

    @Upsert
    suspend fun upsertStock(stock: StockEntity)

    @Transaction
    @Query("SELECT * FROM Stock")
    fun getAllStocks(): Flow<List<StockEntity>>

    @Transaction
    @Query("SELECT * FROM Stock ORDER BY inWatchList DESC")
    fun getAllStocksInUserWatchList(): Flow<List<StockEntity>>

    @Transaction
    @Query("SELECT * FROM Stock WHERE sector = :sector")
    fun getStocksBySector(sector: String): Flow<List<StockEntity>>

    @Transaction
    @Query("SELECT * FROM Stock WHERE ticker = :ticker")
    suspend fun getStockByTicker(ticker: String): StockEntity

    @Transaction
    @Query("UPDATE Stock SET inWatchList = :inWatchList WHERE sid = :sid")
    suspend fun updateStockInUserWatchList(sid: String, inWatchList: Boolean)



    // Indices
    @Transaction
    @Query("SELECT * FROM Indices")
    fun getAllIndices(): Flow<List<IndicesEntity>>

    @Transaction
    @Query("SELECT * FROM Indices ORDER BY inWatchList DESC")
    fun getAllIndicesInUserWatchList(): Flow<List<IndicesEntity>>


    @Transaction
    @Query("SELECT * FROM Indices WHERE sid = :sid")
    suspend fun getIndicesById(sid: String): IndicesEntity

    @Transaction
    @Query("DELETE FROM Indices WHERE sid = :sid")
    suspend fun deleteIndicesById(sid: String)

    @Upsert
    suspend fun upsertIndices(indices: IndicesEntity)

    @Transaction
    @Query("UPDATE Indices SET inWatchList = :inWatchList WHERE sid = :sid")
    suspend fun updateIndicesInUserWatchList(sid: String, inWatchList: Boolean)


    //Asset
    @Transaction
    @Query("SELECT * FROM asset")
    fun getAllAssets(): Flow<List<AssetEntity>>

    @Transaction
    @Query("SELECT * FROM asset WHERE type = :type")
    fun getAssetsByType(type: AssetType): Flow<List<AssetEntity>>

    @Transaction
    @Upsert
    suspend fun upsertAsset(asset: AssetEntity):Long
}
