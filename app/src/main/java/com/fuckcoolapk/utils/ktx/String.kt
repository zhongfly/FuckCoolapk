package com.fuckcoolapk.utils.ktx

fun String.randomLength(range: IntRange) = StringBuilder().apply {
    repeat(range.random()) { append(this@randomLength) }
}.toString()

fun String.contains(items: List<String>): Boolean{
    for (item in items){
        if (item in this){
            return true
        }
    }
    return false
}
