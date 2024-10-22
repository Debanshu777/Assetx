package com.debanshudatta.fintrack.data.domain.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
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
    @Query("SELECT * FROM Stock ORDER BY inUserPortfolio DESC")
    fun getAllStocksInUserProfile(): Flow<List<Stock>>

    @Transaction
    @Query("SELECT * FROM Stock WHERE sector = :sector")
    fun getStocksBySector(sector: String): Flow<List<Stock>>

    @Transaction
    @Query("SELECT * FROM Stock WHERE ticker = :ticker")
    suspend fun getStockByTicker(ticker: String): Stock

    @Transaction
    @Query("UPDATE Stock SET inUserPortfolio = :inUserPortfolio WHERE sid = :sid")
    suspend fun updateStockInUserPortfolio(sid: String, inUserPortfolio: Boolean)



    // Indices
    @Transaction
    @Query("SELECT * FROM Indices")
    fun getAllIndices(): Flow<List<Indices>>

    @Transaction
    @Query("SELECT * FROM Indices ORDER BY inUserPortfolio DESC")
    fun getAllIndicesInUserProfile(): Flow<List<Indices>>


    @Transaction
    @Query("SELECT * FROM Indices WHERE sid = :sid")
    suspend fun getIndicesById(sid: String): Indices

    @Transaction
    @Query("DELETE FROM Indices WHERE sid = :sid")
    suspend fun deleteIndicesById(sid: String)

    @Upsert
    suspend fun upsertIndices(indices: Indices)

    @Transaction
    @Query("UPDATE Indices SET inUserPortfolio = :inUserPortfolio WHERE sid = :sid")
    suspend fun updateIndicesInUserPortfolio(sid: String, inUserPortfolio: Boolean)
}