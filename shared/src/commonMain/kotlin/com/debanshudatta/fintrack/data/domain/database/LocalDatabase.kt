package com.debanshudatta.fintrack.data.domain.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.debanshudatta.fintrack.data.domain.database.entities.AssetEntity
import com.debanshudatta.fintrack.data.domain.model.Indices
import com.debanshudatta.fintrack.data.domain.model.Stock

@Database(entities = [Stock::class, Indices::class, AssetEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
@ConstructedBy(LocalDatabaseConstructor::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object LocalDatabaseConstructor : RoomDatabaseConstructor<LocalDatabase> {
    override fun initialize(): LocalDatabase
}
