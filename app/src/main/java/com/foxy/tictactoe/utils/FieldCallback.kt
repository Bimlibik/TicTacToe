package com.foxy.tictactoe.utils

interface FieldCallback {

    fun saveFieldSize(size: Int)

    fun saveCurrentCellIndex(x: Int, y: Int)

    fun onCellClick(x: Int, y: Int)

}