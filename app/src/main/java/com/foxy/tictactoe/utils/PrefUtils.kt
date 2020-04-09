package com.foxy.tictactoe.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.foxy.tictactoe.App

// For settings
private const val GAME_MODE = "prefs_game_mode"
private const val GAME_MODE_DEFAULT = "Player vs Player"
private const val FIELD_SIZE = "prefs_field_size"
private const val FIELD_SIZE_DEFAULT = 3

// For statistics
private const val PVP_PLAYER_X = "statistics_pvp_player_x"
private const val PVP_PLAYER_O = "statistics_pvp_player_o"
private const val PVA_LAZY_PLAYER_X = "statistics_pva_lazy_player_x"
private const val PVA_LAZY_PLAYER_O = "statistics_pva_lazy_player_o"
private const val PVA_HARD_PLAYER_X = "statistics_pva_hard_player_x"
private const val PVA_HARD_PLAYER_O = "statistics_pva_hard_player_o"

private val prefsSettings by lazy {
    PreferenceManager.getDefaultSharedPreferences(App.get())
}

private val prefsStatistics by lazy {
    App.get().getSharedPreferences("com.foxy.tictactoe_prefs_statistics", Context.MODE_PRIVATE)
}

fun getPrefsGameMode() : String = prefsSettings.getString(GAME_MODE, GAME_MODE_DEFAULT)!!

fun getPrefsFieldSize() : Int = prefsSettings.getInt(FIELD_SIZE, FIELD_SIZE_DEFAULT)

fun savePvPStatistics(playerX: Boolean) {
    val pvp = getPvPStatistics()
    if (playerX) {
        prefsStatistics.edit().putInt(PVP_PLAYER_X, pvp.first + 1).apply()
    } else {
        prefsStatistics.edit().putInt(PVP_PLAYER_O, pvp.second + 1).apply()
    }
}

fun getPvPStatistics(): Pair<Int, Int> {
    val x = prefsStatistics.getInt(PVP_PLAYER_X, 0)
    val o = prefsStatistics.getInt(PVP_PLAYER_O, 0)
    return Pair(x, o)
}

fun savePvALazyStatistics(playerX: Boolean) {
    val pvaLazy = getPvALazyStatistics()
    if (playerX) {
        prefsStatistics.edit().putInt(PVA_LAZY_PLAYER_X, pvaLazy.first + 1).apply()
    } else {
        prefsStatistics.edit().putInt(PVA_LAZY_PLAYER_O, pvaLazy.second + 1).apply()
    }
}

fun getPvALazyStatistics(): Pair<Int, Int> {
    val x = prefsStatistics.getInt(PVA_LAZY_PLAYER_X, 0)
    val o = prefsStatistics.getInt(PVA_LAZY_PLAYER_O, 0)
    return Pair(x, o)
}

fun savePvAHardStatistics(playerX: Boolean) {
    val pvaHard = getPvAHardStatistics()
    if (playerX) {
        prefsStatistics.edit().putInt(PVA_HARD_PLAYER_X, pvaHard.first + 1).apply()
    } else {
        prefsStatistics.edit().putInt(PVA_HARD_PLAYER_O, pvaHard.second + 1).apply()
    }
}

fun getPvAHardStatistics(): Pair<Int, Int> {
    val x = prefsStatistics.getInt(PVA_HARD_PLAYER_X, 0)
    val o = prefsStatistics.getInt(PVA_HARD_PLAYER_O, 0)
    return Pair(x, o)
}