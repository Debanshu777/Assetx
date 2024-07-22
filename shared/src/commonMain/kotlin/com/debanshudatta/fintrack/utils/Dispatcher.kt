package com.debanshudatta.fintrack.utils

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    val io:CoroutineDispatcher
}

internal expect fun provideDispatcher(): Dispatcher