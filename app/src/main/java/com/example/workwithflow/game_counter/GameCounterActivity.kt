package com.example.workwithflow.game_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.workwithflow.R
import com.example.workwithflow.databinding.ActivityGameCounterBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.workwithflow.crypto_app.CryptoViewModel
import kotlinx.coroutines.launch

class GameCounterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameCounterBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[TeamScoreViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeViewModel()
        setupListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    when (it) {
                        is TeamScoreState.Game -> {
                            binding.team1Score.text = it.score1.toString()
                            binding.team2Score.text = it.score2.toString()
                        }
                        is TeamScoreState.Winner -> {
                            binding.team1Score.text = it.score1.toString()
                            binding.team2Score.text = it.score2.toString()
                            Toast.makeText(
                                this@GameCounterActivity,
                                "Winner: ${it.winnerTeam}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

    }

    private fun setupListeners() {
        binding.team1Logo.setOnClickListener {
            viewModel.increaseScore(Team.TEAM_1)
        }
        binding.team2Logo.setOnClickListener {
            viewModel.increaseScore(Team.TEAM_2)
        }
    }
}