package com.debanshudatta.fintrack.data.domain.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.debanshudatta.fintrack.utils.Dispatcher

fun getLocalDatabase(context: Context,dispatcher: Dispatcher): LocalDatabase {
    val dbFile = context.getDatabasePath("assetx.db")
    return Room.databaseBuilder<LocalDatabase>(
        context.applicationContext,
        dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcher.io)
        .build()
}
