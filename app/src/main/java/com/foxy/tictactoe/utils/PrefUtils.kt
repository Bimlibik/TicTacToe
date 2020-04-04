package com.foxy.tictactoe.utils

import androidx.preference.PreferenceManager
import com.foxy.tictactoe.App

private const val GAME_MODE = "prefs_game_mode"
private const val GAME_MODE_DEFAULT = "Player vs Player"
private const val FIELD_SIZE = "prefs_field_size"
private const val FIELD_SIZE_DEFAULT = 3

private val prefs by lazy {
    PreferenceManager.getDefaultSharedPreferences(App.get())
}

fun getGameMode() : String = prefs.getString(GAME_MODE, GAME_MODE_DEFAULT)!!

fun getFieldSize() : Int = prefs.getInt(FIELD_SIZE, FIELD_SIZE_DEFAULT)