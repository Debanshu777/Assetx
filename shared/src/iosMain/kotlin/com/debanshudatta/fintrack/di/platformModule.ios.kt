package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.data.domain.database.getLocalDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { getLocalDatabase(get()) }
}
