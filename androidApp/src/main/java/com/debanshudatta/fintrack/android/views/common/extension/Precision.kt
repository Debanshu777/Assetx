package com.debanshudatta.fintrack.android.views.common.extension

import kotlin.math.roundToInt

fun Double.roundTo(): Double = this.times(100.0).roundToInt() / 100.0
fun Long.roundTo(): Double = this.times(100.0).roundToInt() / 100.0
fun Float.roundTo(): Double = this.times(100.0).roundToInt() / 100.0
