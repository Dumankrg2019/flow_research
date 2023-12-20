package com.example.workwithflow

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {

    val scope = CoroutineScope(Dispatchers.IO)

    val job = scope.launch {
        println("getting data")
        delay(3000)
        println("delay is over")

    }

    job.join()
    println("finish waithin loading data")
}