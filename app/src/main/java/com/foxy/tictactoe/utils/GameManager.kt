package com.foxy.tictactoe.utils

import android.util.Log
import com.foxy.tictactoe.data.Cell

class GameManager() {

    fun getDot(cell: Cell, playerX: Boolean) : Dot {
        if (isCellValid(cell)) {
            return when(playerX) {
                true -> Dot.X
                else -> Dot.O
            }
        }
        return Dot.EMPTY
    }

    fun isWin(index: Pair<Int, Int>, field: Array<Array<Cell>>, dot: Dot,
                      winLength: Int): Pair<Boolean, Array<Cell>> {
        val winCells = Array(winLength){ Cell() }
        val isWin = checkColumn(index.first, field, dot, winLength, winCells) ||
                checkRow(index.second, field,dot, winLength, winCells) ||
                checkDiagonalFromLeftToRight(field, dot, winLength, winCells) ||
                checkDiagonalFromRightToLeft(field, dot, winLength, winCells)
        return Pair(isWin, winCells)
    }

    private fun checkColumn(x: Int, field: Array<Array<Cell>>, dot: Dot, winLength: Int,
                            winCells: Array<Cell>) : Boolean {
        for (y in 0 until winLength) {
            if (field[x][y].dot != dot) {
                return false
            }
        }

        for (i in 0 until winLength) {
            winCells[i] = field[x][i]
        }
        return true
    }

    private fun checkRow(y: Int, field: Array<Array<Cell>>, dot: Dot, winLength: Int,
                         winCells: Array<Cell>) : Boolean {
        for (x in 0 until winLength) {
            if (field[x][y].dot != dot) {
                return false
            }
        }

        for (i in 0 until winLength) {
            winCells[i] = field[i][y]
        }
        return true
    }

    private fun checkDiagonalFromLeftToRight(field: Array<Array<Cell>>, dot: Dot, winLength: Int,
                                             winCells: Array<Cell>) : Boolean {
        for(i in 0 until winLength) {
            if (field[i][i].dot != dot) {
                return false
            }
            winCells[i] = field[i][i]
        }
        return true
    }

    private fun checkDiagonalFromRightToLeft(field: Array<Array<Cell>>, dot: Dot, winLength: Int,
                                             winCells: Array<Cell>) : Boolean {
        // right to left
        var count = 1
        for (i in 0 until winLength) {
            if (field[winLength - count][i].dot != dot) {
                return false
            }
            winCells[i] = field[winLength - count][i]
            count++
        }
        return true
    }

    private fun isCellValid(cell: Cell) : Boolean {
        return cell.dot == Dot.EMPTY
    }

    fun isFieldFull(field: Array<Array<Cell>>) : Boolean {
        for (cells in field) {
            for (cell in cells) {
                if (cell.isEmpty) return false
            }
        }
        return true
    }
}