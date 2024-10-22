package com.debanshudatta.fintrack.data.domain.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getLocalDatabase():LocalDatabase{
    val dbFile = NSHomeDirectory() + "/local.db"
    return Room.databaseBuilder<LocalDatabase>(
        name = dbFile,
        factory =  {LocalDatabase::class.instantiateImpl()}
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}