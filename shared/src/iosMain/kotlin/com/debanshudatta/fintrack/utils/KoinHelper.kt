package com.debanshudatta.fintrack.utils

import com.debanshudatta.fintrack.data.AppViewModel
import com.debanshudatta.fintrack.di.getSharedModules
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.component.get

fun initKoin() {
    startKoin {
        modules(getSharedModules())
    }
}

class KoinHelper: KoinComponent {
    fun getAppViewModel() = get<AppViewModel>()
}
