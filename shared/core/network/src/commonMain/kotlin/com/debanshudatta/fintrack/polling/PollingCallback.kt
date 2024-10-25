package com.debanshudatta.fintrack.polling

interface PollingCallback {
    suspend fun onPoll()
    fun onError(exception: Exception)
}
