package com.example.workwithflow.game_counter

sealed class TeamScoreState {
    data class Game(
        val score1: Int,
        val score2: Int
    ): TeamScoreState()

    data class Winner(
        val winnerTeam: Team,
        val score1: Int,
        val score2: Int
    ): TeamScoreState()
}
