package com.example.workwithflow.crypto_app.data

sealed class State{

    object Initial: State()
    object Loading: State()
    data class Content(val currencyList:List<Currency>): State()
}
