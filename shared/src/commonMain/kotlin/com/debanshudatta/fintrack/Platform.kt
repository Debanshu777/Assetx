package com.debanshudatta.fintrack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform