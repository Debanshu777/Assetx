package com.debanshudatta.fintrack.data.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.debanshudatta.fintrack.data.domain.model.Indices
import com.debanshudatta.fintrack.data.domain.model.Stock

@Database(entities = [Stock::class,Indices::class], version = 1)
@TypeConverters(Converters::class)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun localDao(): LocalDao
}