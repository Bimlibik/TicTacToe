package com.foxy.tictactoe.data

interface GameRepository {

    fun getGameMode(): String

    fun getFirstStep(): String

    fun getFieldSize(): Int

    fun getWinLineLength(): Int

    fun saveWinLineLength(winLength: Int)

    fun getStatistics(gameMode: String): Pair<Int, Int>

    fun saveStatistics(gameMode: String, playerX: Boolean)
}