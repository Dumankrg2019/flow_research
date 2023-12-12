package com.example.workwithflow.newFlowLesson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)
suspend fun main() {
    val flow = getFlow()

    var job: Job? = null
    var job2: Job? = null
    job = coroutineScope.launch {
        flow.collect{
            println(it)
        }
    }

    delay(3000)
    job2 = coroutineScope.launch {
        flow.collect{
            println(it)
        }
    }
    job.join()
    job2.join()

}

fun getFlow(): Flow<Int> = flow{
    repeat(100) {
        println("Emited: $it")
        emit(it)
        delay(1000)
    }
}