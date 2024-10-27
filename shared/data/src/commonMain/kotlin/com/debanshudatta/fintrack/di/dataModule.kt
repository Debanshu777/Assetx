package com.debanshudatta.fintrack.data.di

import com.debanshudatta.fintrack.data.repository.LocalDataRepository
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import org.koin.dsl.module

val dataModule = module {
    single { RemoteDataRepository() }
    single { LocalDataRepository(get()) }
}