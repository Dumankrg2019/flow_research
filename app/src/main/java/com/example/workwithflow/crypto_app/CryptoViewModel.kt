package com.example.workwithflow.crypto_app


import androidx.lifecycle.viewModelScope
import com.example.workwithflow.crypto_app.data.CryptoRepository
import com.example.workwithflow.crypto_app.data.State

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart


class CryptoViewModel : ViewModel() {

    val repository = CryptoRepository


    //стало вот так
    val state: LiveData<State> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State}
        .onStart { emit(State.Loading) }
        .asLiveData()

    // было вот так
//    init {
//        loadData()
//    }
//
//    private  fun loadData() {
//        repository.getCurrencyList()
//            .filter { it.isNotEmpty() }
//            .map { State.Content(currencyList = it) as State}
//            .onStart { emit(State.Loading) }
//            .onEach { _state.value = it }
//            .asLiveData()
//    }
}
