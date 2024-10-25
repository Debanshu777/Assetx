package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.getLocalDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { getLocalDatabase(get()) }
}