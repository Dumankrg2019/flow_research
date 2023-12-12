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
    private var job: Job? = null

    //стало вот так
    private val _state = MutableLiveData<State>(State.Initial)
    val state: LiveData<State> = _state

    // было вот так

//
      fun loadData() {
       job =  repository.getCurrencyList()
            .onStart {
                _state.value = State.Loading
            }
            .filter { it.isNotEmpty() }
            .onEach {
                Log.e("loadData", "start flow")
                _state.value = State.Content(currencyList = it)
            }
            .launchIn(viewModelScope)
    }

    fun stopLoading() {
        job?.cancel()
    }
}
