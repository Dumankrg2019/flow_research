package com.example.workwithflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UsersRepository {

    private val users = mutableListOf("Duman", "Aslan", "Arman", "Alikhan")

    suspend fun addUser(user: String) {
        users.add(user)
        delay(50)
    }

    suspend fun loadUsers(): Flow<List<String>> = flow {
        while (true) {
            emit(users.toList())
            delay(500)
        }
    }
}