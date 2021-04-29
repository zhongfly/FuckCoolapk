package com.fuckcoolapk.utils.ktx

fun String.randomLength(range: IntRange) = StringBuilder().apply {
    repeat(range.random()) { append(this@randomLength) }
}.toString()
