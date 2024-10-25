package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.data.AppViewModel
import com.debanshudatta.fintrack.data.domain.usecases.GetStockAssetDataUseCase
import com.debanshudatta.fintrack.data.domain.usecases.HomeScreenDataUseCase
import com.debanshudatta.fintrack.data.domain.usecases.IndicesDataUseCase
import com.debanshudatta.fintrack.data.repository.LocalDataRepository
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import org.koin.dsl.module

private val dataModule = module {
    single { RemoteDataRepository() }
    single { LocalDataRepository(get()) }
    factory { HomeScreenDataUseCase() }
    factory { IndicesDataUseCase() }
    factory { GetStockAssetDataUseCase() }
    single { AppViewModel(get(), get(), get()) }
}

private val sharedModules = listOf(
    dataModule, platformModule
)

fun getSharedModules() = sharedModules + databaseModule
