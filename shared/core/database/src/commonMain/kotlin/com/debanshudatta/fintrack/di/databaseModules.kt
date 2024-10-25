package com.debanshudatta.fintrack.di

import com.debanshudatta.fintrack.utils.provideDispatcher
import org.koin.dsl.module

private val databaseUtilityModule = module {
    factory { provideDispatcher() }
}

val databaseModule = listOf(
    databaseUtilityModule, platformModule
)