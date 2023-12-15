package com.example.workwithflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workwithflow.crypto_app.CryptoActivity
import com.example.workwithflow.databinding.ActivityNavigateBinding
import com.example.workwithflow.game_counter.GameCounterActivity

class NavigateActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNavigateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListener()
    }

    private fun setListener() {
        binding.btnGoToCrypto.setOnClickListener {
            Intent(this, CryptoActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.btnGoToGameCounter.setOnClickListener {
            Intent(this, GameCounterActivity::class.java).apply {
                startActivity(this)
            }
        }
    }


}