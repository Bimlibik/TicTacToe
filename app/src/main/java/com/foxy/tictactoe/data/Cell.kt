package com.foxy.tictactoe.data

import com.foxy.tictactoe.utils.Dot

class Cell (
    var dot: Dot = Dot.EMPTY,
    var left: Int = 0,
    var top: Int = 0,
    var right: Int = 0,
    var bottom: Int = 0
) {
    val isEmpty get() : Boolean {
       return when (dot) {
           Dot.EMPTY -> true
           else -> false
       }
    }

    fun contains(x: Int, y: Int) : Boolean {
        return x in (left + 1) until right && y in (top + 1) until bottom
    }

}