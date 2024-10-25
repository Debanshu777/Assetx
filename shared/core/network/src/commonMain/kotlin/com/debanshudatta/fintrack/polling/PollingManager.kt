package com.debanshudatta.fintrack.polling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PollingManager(private val callback: PollingCallback) {
    private var pollingJob: Job? = null

    fun startPolling(scope: CoroutineScope) {
        pollingJob = scope.launch {
            while (isActive) {
                try {
                    callback.onPoll()
                } catch (e: Exception) {
                    callback.onError(e)
                    stopPolling()
                }
                delay(20000)
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
    }
}
