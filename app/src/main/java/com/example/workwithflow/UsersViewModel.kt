package com.example.workwithflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val repository = UsersRepository

    private val _users = MutableLiveData<List<String>>()
    val users: LiveData<List<String>> = _users

    init {
        loadUser()
    }

    fun addUser(user: String) {
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            repository.loadUsers()
                .collect {
                    _users.value = it
                }
        }
    }
}