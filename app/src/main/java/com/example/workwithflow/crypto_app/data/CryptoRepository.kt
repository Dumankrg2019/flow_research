package com.example.workwithflow.crypto_app.data

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object CryptoRepository {

    private val currencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val currencyList = mutableListOf<Currency>()

    /*создали hot flow для избавлеия о бесконечного цикла, которы
    был решенеим при наличии только cold flow
    */
    private val refreshEvents = MutableSharedFlow<Unit>()

     fun getCurrencyList(): Flow<List<Currency>> = flow{
         delay(3000)
        generateCurrencyList()
        emit(currencyList.toList())
        refreshEvents.collect{
            delay(3000)
            generateCurrencyList()
            emit(currencyList.toList())
        }
    }

    suspend fun refreshList() {
        refreshEvents.emit(Unit)
    }
    private fun generateCurrencyList() {
        val prices = buildList {
            repeat(currencyNames.size) {
                add(Random.nextInt(1000, 2000))
            }
        }
        val newData = buildList {
            for ((index, currencyName) in currencyNames.withIndex()) {
                val price = prices[index]
                val currency = Currency(name = currencyName, price = price)
                add(currency)
            }
        }
        currencyList.clear()
        currencyList.addAll(newData)
    }
}