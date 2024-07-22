package com.debanshudatta.fintrack.data.domain.polling

interface PollingCallback {
    suspend fun onPoll()
    fun onError(exception: Exception)
}