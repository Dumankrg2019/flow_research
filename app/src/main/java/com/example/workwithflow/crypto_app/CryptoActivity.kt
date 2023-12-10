package com.example.workwithflow.crypto_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.workwithflow.R
import com.example.workwithflow.crypto_app.data.CryptoAdapter
import com.example.workwithflow.crypto_app.data.State
import com.example.workwithflow.databinding.ActivityCryptoBinding

class CryptoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCryptoBinding.inflate(layoutInflater)
    }


    private val viewModel by lazy {
        ViewModelProvider(this)[CryptoViewModel::class.java]
    }

    private val adapter = CryptoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        binding.recyclerViewCurrencyPriceList.adapter = adapter
        binding.recyclerViewCurrencyPriceList.itemAnimator = null
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            when (it) {
                is State.Initial -> {
                    binding.progressBarLoading.isVisible = false
                }
                is State.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is State.Content -> {
                    binding.progressBarLoading.isVisible = false
                    adapter.submitList(it.currencyList)
                }
            }
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, CryptoActivity::class.java)
    }
}