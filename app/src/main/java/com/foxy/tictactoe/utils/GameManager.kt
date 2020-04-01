package com.foxy.tictactoe.utils

import com.foxy.tictactoe.data.Cell

class GameManager() {

    private var winLine = Win.HORIZONTAL

    fun getStartCoordinates(winCells: Array<Cell>, halfCellSize: Int) : Pair<Float, Float> {
        var x = 0f
        var y = 0f

        when(winLine) {
            Win.HORIZONTAL -> {
                x = winCells.first().left.toFloat()
                y = (winCells.first().top + halfCellSize).toFloat()
            }
            Win.VERTICAL -> {
                x = (winCells.first().left + halfCellSize).toFloat()
                y = winCells.first().top.toFloat()
            }
            Win.DIAGONAL_LEFT -> {
                x = winCells.first().left.toFloat()
                y = winCells.first().top.toFloat()
            }
            Win.DIAGONAL_RIGHT -> {
                x = winCells.first().right.toFloat()
                y = winCells.first().top.toFloat()
            }
        }
        return Pair(x, y)
    }

    fun getEndCoordinates(winCells: Array<Cell>, halfCellSize: Int) : Pair<Float, Float> {
        var x = 0f
        var y = 0f

        when(winLine) {
            Win.HORIZONTAL -> {
                x = winCells.last().right.toFloat()
                y = (winCells.first().top + halfCellSize).toFloat()
            }
            Win.VERTICAL -> {
                x = (winCells.first().left + halfCellSize).toFloat()
                y = winCells.last().bottom.toFloat()
            }
            Win.DIAGONAL_LEFT -> {
                x = winCells.last().right.toFloat()
                y = winCells.last().bottom.toFloat()
            }
            Win.DIAGONAL_RIGHT -> {
                x = winCells.last().left.toFloat()
                y = winCells.last().bottom.toFloat()
            }
        }
        return Pair(x, y)
    }

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
        winLine = Win.VERTICAL
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
        winLine = Win.HORIZONTAL
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
        winLine = Win.DIAGONAL_LEFT
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
        winLine = Win.DIAGONAL_RIGHT
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