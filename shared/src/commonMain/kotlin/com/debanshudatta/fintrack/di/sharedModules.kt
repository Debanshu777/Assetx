package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.data.AppViewModel
import com.debanshudatta.fintrack.data.domain.usecases.GetStockAssetDataUseCase
import com.debanshudatta.fintrack.data.domain.usecases.HomeScreenDataUseCase
import com.debanshudatta.fintrack.data.domain.usecases.IndicesDataUseCase
import com.debanshudatta.fintrack.data.repository.LocalDataRepository
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import com.debanshudatta.fintrack.utils.provideDispatcher
import org.koin.dsl.module

private val dataModule = module {
    single { RemoteDataRepository() }
    single { LocalDataRepository(get()) }
    factory { HomeScreenDataUseCase() }
    factory { IndicesDataUseCase() }
    factory { GetStockAssetDataUseCase() }
    single { AppViewModel(get(), get(), get()) }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val sharedModules = listOf(
    dataModule, utilityModule, platformModule
)

fun getSharedModules() = sharedModules
