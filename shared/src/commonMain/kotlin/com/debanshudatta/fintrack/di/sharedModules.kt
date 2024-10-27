package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.AppViewModel
import com.debanshudatta.fintrack.data.di.dataModule
import com.debanshudatta.fintrack.usecase.GetStockAssetDataUseCase
import com.debanshudatta.fintrack.usecase.HomeScreenDataUseCase
import com.debanshudatta.fintrack.usecase.IndicesDataUseCase
import org.koin.dsl.module

private val domainModule = module {
    factory { HomeScreenDataUseCase() }
    factory { IndicesDataUseCase() }
    factory { GetStockAssetDataUseCase() }
    single { AppViewModel(get(), get(), get()) }
}

private val sharedModules = listOf(
    domainModule, platformModule
)

fun getSharedModules() = sharedModules + databaseModule + dataModule
