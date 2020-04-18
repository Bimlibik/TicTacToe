package com.foxy.tictactoe.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.foxy.tictactoe.App
import com.foxy.tictactoe.utils.enums.GameMode

// For settings
private const val GAME_MODE = "prefs_game_mode"
private const val GAME_MODE_DEFAULT = GameMode.PvA_Lazy
private const val FIRST_STEP = "prefs_first_step"
private const val FIRST_STEP_DEFAULT = "Player"
private const val FIELD_SIZE = "prefs_field_size"
private const val FIELD_SIZE_DEFAULT = 3
private const val WIN_LINE_LENGTH = "prefs_win_length"
private const val WIN_LINE_LENGTH_DEFAULT = 3

// For statistics
private const val PVP_PLAYER_X = "statistics_pvp_player_x"
private const val PVP_PLAYER_O = "statistics_pvp_player_o"
private const val PVA_LAZY_PLAYER = "statistics_pva_lazy_player"
private const val PVA_LAZY_AI = "statistics_pva_lazy_ai"
private const val PVA_HARD_PLAYER = "statistics_pva_hard_player"
private const val PVA_HARD_AI = "statistics_pva_hard_ai"

private val prefsSettings by lazy {
    PreferenceManager.getDefaultSharedPreferences(App.get())
}

private val prefsStatistics by lazy {
    App.get().getSharedPreferences("com.foxy.tictactoe_prefs_statistics", Context.MODE_PRIVATE)
}

fun getGameModeFromPrefs() : String = prefsSettings.getString(GAME_MODE, GAME_MODE_DEFAULT)!!

fun getFirstStepFromPrefs() : String = prefsSettings.getString(FIRST_STEP, FIRST_STEP_DEFAULT)!!

fun saveFirstStepToPrefs(firstStep: String) {
    prefsSettings.edit().putString(FIRST_STEP, firstStep).apply()
}

fun getFieldSizeFromPrefs() : Int = prefsSettings.getInt(FIELD_SIZE, FIELD_SIZE_DEFAULT)

fun getsWinLineLengthFromPrefs() : Int = prefsSettings.getInt(WIN_LINE_LENGTH, WIN_LINE_LENGTH_DEFAULT)

fun saveWinLineLengthToPrefs(winLength: Int) {
    prefsSettings.edit().putInt(WIN_LINE_LENGTH, winLength).apply()
}

fun savePvPStatisticsToPrefs(playerX: Boolean) {
    val pvp = getPvPStatisticsFromPrefs()
    if (playerX) {
        prefsStatistics.edit().putInt(PVP_PLAYER_X, pvp.first + 1).apply()
    } else {
        prefsStatistics.edit().putInt(PVP_PLAYER_O, pvp.second + 1).apply()
    }
}

fun getPvPStatisticsFromPrefs(): Pair<Int, Int> {
    val x = prefsStatistics.getInt(PVP_PLAYER_X, 0)
    val o = prefsStatistics.getInt(PVP_PLAYER_O, 0)
    return Pair(x, o)
}

fun savePvALazyStatisticsToPrefs(winPlayer: Boolean) {
    val pvaLazy = getPvALazyStatisticsFromPrefs()
    if (winPlayer) {
        prefsStatistics.edit().putInt(PVA_LAZY_PLAYER, pvaLazy.first + 1).apply()
    } else {
        prefsStatistics.edit().putInt(PVA_LAZY_AI, pvaLazy.second + 1).apply()
    }
}

fun getPvALazyStatisticsFromPrefs(): Pair<Int, Int> {
    val player = prefsStatistics.getInt(PVA_LAZY_PLAYER, 0)
    val ai = prefsStatistics.getInt(PVA_LAZY_AI, 0)
    return Pair(player, ai)
}

fun savePvAHardStatisticsToPrefs(playerX: Boolean) {
    val pvaHard = getPvAHardStatisticsFromPrefs()
    if (playerX) {
        prefsStatistics.edit().putInt(PVA_HARD_PLAYER, pvaHard.first + 1).apply()
    } else {
        prefsStatistics.edit().putInt(PVA_HARD_AI, pvaHard.second + 1).apply()
    }
}

fun getPvAHardStatisticsFromPrefs(): Pair<Int, Int> {
    val x = prefsStatistics.getInt(PVA_HARD_PLAYER, 0)
    val o = prefsStatistics.getInt(PVA_HARD_AI, 0)
    return Pair(x, o)
}