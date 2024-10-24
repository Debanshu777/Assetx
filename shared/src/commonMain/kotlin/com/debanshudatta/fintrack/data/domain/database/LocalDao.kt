package com.debanshudatta.fintrack.data.domain.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.debanshudatta.fintrack.data.domain.database.entities.AssetEntity
import com.debanshudatta.fintrack.data.domain.database.entities.AssetType
import com.debanshudatta.fintrack.data.domain.model.Indices
import com.debanshudatta.fintrack.data.domain.model.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    // Stocks
    @Transaction
    @Query("SELECT * FROM Stock WHERE sid = :sid")
    suspend fun getStockById(sid: String): Stock

    @Transaction
    @Query("DELETE FROM Stock WHERE sid = :sid")
    suspend fun deleteStockById(sid: String)

    @Upsert
    suspend fun upsertStock(stock: Stock)

    @Transaction
    @Query("SELECT * FROM Stock")
    fun getAllStocks(): Flow<List<Stock>>

    @Transaction
    @Query("SELECT * FROM Stock ORDER BY inWatchList DESC")
    fun getAllStocksInUserWatchList(): Flow<List<Stock>>

    @Transaction
    @Query("SELECT * FROM Stock WHERE sector = :sector")
    fun getStocksBySector(sector: String): Flow<List<Stock>>

    @Transaction
    @Query("SELECT * FROM Stock WHERE ticker = :ticker")
    suspend fun getStockByTicker(ticker: String): Stock

    @Transaction
    @Query("UPDATE Stock SET inWatchList = :inWatchList WHERE sid = :sid")
    suspend fun updateStockInUserWatchList(sid: String, inWatchList: Boolean)



    // Indices
    @Transaction
    @Query("SELECT * FROM Indices")
    fun getAllIndices(): Flow<List<Indices>>

    @Transaction
    @Query("SELECT * FROM Indices ORDER BY inWatchList DESC")
    fun getAllIndicesInUserWatchList(): Flow<List<Indices>>


    @Transaction
    @Query("SELECT * FROM Indices WHERE sid = :sid")
    suspend fun getIndicesById(sid: String): Indices

    @Transaction
    @Query("DELETE FROM Indices WHERE sid = :sid")
    suspend fun deleteIndicesById(sid: String)

    @Upsert
    suspend fun upsertIndices(indices: Indices)

    @Transaction
    @Query("UPDATE Indices SET inWatchList = :inWatchList WHERE sid = :sid")
    suspend fun updateIndicesInUserWatchList(sid: String, inWatchList: Boolean)


    //Asset
    @Transaction
    @Query("SELECT * FROM Assets")
    fun getAllAssets(): Flow<List<AssetEntity>>

    @Transaction
    @Query("SELECT * FROM Assets WHERE type = :type")
    fun getAssetsByType(type: AssetType): Flow<List<AssetEntity>>

    @Transaction
    @Upsert
    suspend fun upsertAsset(asset: AssetEntity):Long
}
