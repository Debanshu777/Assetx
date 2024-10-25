package com.debanshudatta.fintrack

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.debanshudatta.fintrack.utils.Dispatcher
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getLocalDatabase(dispatcher: Dispatcher): LocalDatabase {
    val dbFile = documentDirectory() + "/assetx.db"
    return Room.databaseBuilder<LocalDatabase>(
        name = dbFile,
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcher.io)
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
