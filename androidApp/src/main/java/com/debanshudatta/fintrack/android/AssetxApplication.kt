package com.debanshudatta.fintrack.android

import android.app.Application
import com.debanshudatta.fintrack.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AssetxApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AssetxApplication)
            modules(getSharedModules())
        }
    }
}