package com.debanshudatta.fintrack.data.domain.model

enum class Type(val value: String) {
    GAINERS("gainers"),
    ACTIVE("active"),
    LOSERS("losers"),
    APPROACHING_HIGH("approachingHigh"),
    APPROACHING_LOW("approachingLow")
}
