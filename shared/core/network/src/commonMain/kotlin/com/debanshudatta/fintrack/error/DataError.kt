package com.debanshudatta.fintrack.error

sealed interface DataError : Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        SERIALIZATION,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        UNKNOWN,
    }
    enum class Local: DataError {
        DISK_FULL
    }
}
