package com.example.workwithflow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.workwithflow.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UsersViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getFlowNumber()
            .map {
                Log.e("flow status", "$it")
            }
        setupListener()
        observeViewModel()
    }

    private fun setupListener() {
        binding.btnAddUser.setOnClickListener {
            binding.etName.text.toString()
                .trim()
                .takeIf { it.isNotBlank() }
                ?.let {
                    viewModel.addUser(it)
                }
        }
        binding.btnNexActivity.setOnClickListener {
            startActivity(UserActivity2.newIntent2(this))
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) {
            binding.tvViewUsers.text = it.joinToString()
        }
    }


    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    fun getFlowNumber(): Flow<Int> {
        return flow {
            emit(7)
            emit(10)
            emit(15)
            emit(5)
        }
    }
}