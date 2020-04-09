package com.foxy.tictactoe.data

interface GameRepository {

    fun getGameMode(): String

    fun getFieldSize(): Int

    fun getStatistics(gameMode: String): Pair<Int, Int>

    fun saveStatistics(gameMode: String, playerX: Boolean)
}