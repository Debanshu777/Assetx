package com.debanshudatta.fintrack.data.domain.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getLocalDatabase(context: Context): LocalDatabase {
    val dbFile = context.getDatabasePath("local.db")
    return Room.databaseBuilder<LocalDatabase>(
        context.applicationContext,
        dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}