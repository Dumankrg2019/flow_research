package com.example.workwithflow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.workwithflow.databinding.ActivityUser2Binding

class UserActivity2 : AppCompatActivity() {

    private val binding by lazy {
        ActivityUser2Binding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UsersViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonAddUser.setOnClickListener {
            binding.editTextUsername.text.toString()
                .trim()
                .takeIf { it.isNotBlank() }
                ?.let {
                    viewModel.addUser(it)
                }
        }
        viewModel.users.observe(this) {
            binding.textViewUsers.text = it.joinToString()
        }
    }

    companion object {
        fun newIntent2(context: Context) = Intent(context, UserActivity2::class.java)
    }
}