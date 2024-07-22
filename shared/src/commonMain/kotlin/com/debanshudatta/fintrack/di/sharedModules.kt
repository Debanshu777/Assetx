package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.data.AppViewModel
import com.debanshudatta.fintrack.data.domain.usecases.HomeScreenDataUseCase
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import com.debanshudatta.fintrack.utils.provideDispatcher
import org.koin.dsl.module

private val dataModule = module {
    single { RemoteDataRepository() }
    factory { HomeScreenDataUseCase() }
    single { AppViewModel(get()) }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val sharedModules = listOf(
    dataModule, utilityModule
)

fun getSharedModules() = sharedModules