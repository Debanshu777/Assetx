package com.debanshudatta.fintrack

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.debanshudatta.fintrack.entities.AssetEntity
import com.debanshudatta.fintrack.entities.IndicesEntity
import com.debanshudatta.fintrack.entities.StockEntity

@Database(
    entities = [StockEntity::class, IndicesEntity::class, AssetEntity::class],
    version = 1,
    exportSchema = true
)
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
