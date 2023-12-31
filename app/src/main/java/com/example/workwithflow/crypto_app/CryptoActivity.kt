package com.example.workwithflow.crypto_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.workwithflow.R
import com.example.workwithflow.crypto_app.data.CryptoAdapter
import com.example.workwithflow.crypto_app.data.State
import com.example.workwithflow.databinding.ActivityCryptoBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        binding.btnRefresh.setOnClickListener {
            viewModel.refreshList()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCurrencyPriceList.adapter = adapter
        binding.recyclerViewCurrencyPriceList.itemAnimator = null
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect{
                    when (it) {
                        is State.Initial -> {
                            binding.progressBarLoading.isVisible = false
                            binding.btnRefresh.isEnabled = false
                        }
                        is State.Loading -> {
                            binding.progressBarLoading.isVisible = true
                            binding.btnRefresh.isEnabled = false
                        }
                        is State.Content -> {
                            binding.progressBarLoading.isVisible = false
                            adapter.submitList(it.currencyList)
                            binding.btnRefresh.isEnabled = true

                        }
                    }
                }
            }
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, CryptoActivity::class.java)
    }
}