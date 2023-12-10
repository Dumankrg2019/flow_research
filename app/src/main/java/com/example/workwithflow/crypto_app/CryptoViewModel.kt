package com.example.workwithflow.crypto_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workwithflow.crypto_app.data.CryptoRepository
import com.example.workwithflow.crypto_app.data.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    val repository = CryptoRepository

    private val _state = MutableLiveData<State>(State.Initial)
    val state: LiveData<State> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            while (true) {
                val currentState = _state.value
                if (currentState !is State.Content || currentState.currencyList.isEmpty()) {
                    _state.value = State.Loading
                }
                val currentList = repository.getCurrencyList()
                _state.value = State.Content(currencyList = currentList)
                delay(3000)
            }
        }
    }
}
