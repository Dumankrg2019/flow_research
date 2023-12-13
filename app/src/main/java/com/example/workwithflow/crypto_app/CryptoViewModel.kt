package com.example.workwithflow.crypto_app


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workwithflow.crypto_app.data.CryptoRepository
import com.example.workwithflow.crypto_app.data.State

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class CryptoViewModel : ViewModel() {

    val repository = CryptoRepository

    private val loadingFlow = MutableSharedFlow<State>()

    val state: Flow<State> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State }
        .onStart {
            Log.e("ViewModel", "Started")
            emit(State.Loading)
        }
        .onEach {
            Log.e("ViewModel", "OnEach start flow")
        }
        .onCompletion {
            Log.e("ViewModel", "onCompletion")
       }
        .mergeWith(loadingFlow)

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
        return merge(this, another)
    }

    fun refreshList() {
        viewModelScope.launch {
            loadingFlow.emit(State.Loading)
            repository.refreshList()
        }
    }
}
