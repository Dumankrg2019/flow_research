package com.example.workwithflow.error_handling

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.Exception
import java.lang.RuntimeException

suspend fun main() {

    createFlowInt()
        .map { State.Content(it) as State }
        .onStart { emit(State.Loading) }
        .catch { println("Caught error") }
        .collect{
        when(it){
            is State.Content -> {
                println("collected: ${it.value}")
                throw RuntimeException()
            }
            State.Error -> {
                println("something went wrong")
            }
            State.Error -> {
                println("Loading...")
            }
            else -> {}
        }
    }
}

private fun createFlowInt(): Flow<Int> = flow {
    repeat(5) {
        delay(500)
        emit(it)
    }
    throw RuntimeException()
}

sealed class State() {
    data class Content(val value: Int): State()

    object Error: State()

    object Loading: State()
}