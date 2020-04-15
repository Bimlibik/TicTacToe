package com.foxy.tictactoe.utils

import com.foxy.tictactoe.data.Cell
import toothpick.InjectConstructor
import java.util.*

@InjectConstructor
class GameManager {

    private var winLine = Win.HORIZONTAL

    fun getStartWinCoordinates(winCells: Array<Cell>, halfCellSize: Int): Pair<Float, Float> {
        var x = 0f
        var y = 0f

        when (winLine) {
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

                for (cell in winCells) {
                    if (cell.left.toFloat() < x) {
                        x = cell.left.toFloat()
                    }
                    if (cell.top.toFloat() < y) {
                        y = cell.top.toFloat()
                    }
                }
            }
            Win.DIAGONAL_RIGHT -> {
                x = winCells.first().right.toFloat()
                y = winCells.first().top.toFloat()

                for (cell in winCells) {
                    if (cell.right.toFloat() > x) {
                        x = cell.right.toFloat()
                    }
                    if (cell.top.toFloat() < y) {
                        y = cell.top.toFloat()
                    }
                }
            }
        }
        return Pair(x, y)
    }

    fun getEndWinCoordinates(winCells: Array<Cell>, halfCellSize: Int): Pair<Float, Float> {
        var x = 0f
        var y = 0f

        when (winLine) {
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

                for (cell in winCells) {
                    if (cell.right.toFloat() > x) {
                        x = cell.right.toFloat()
                    }

                    if (cell.bottom.toFloat() > y) {
                        y = cell.bottom.toFloat()
                    }
                }
            }
            Win.DIAGONAL_RIGHT -> {
                x = winCells.last().left.toFloat()
                y = winCells.last().bottom.toFloat()

                for (cell in winCells) {
                    if (cell.left.toFloat() < x) {
                        x = cell.left.toFloat()
                    }
                    if (cell.bottom.toFloat() > y) {
                        y = cell.bottom.toFloat()
                    }
                }
            }
        }
        return Pair(x, y)
    }

    fun getDot(cell: Cell, playerX: Boolean): Dot {
        if (isCellValid(cell)) {
            return when (playerX) {
                true -> Dot.X
                else -> Dot.O
            }
        }
        return Dot.EMPTY
    }

    fun isWin(index: Pair<Int, Int>, field: Array<Array<Cell>>, dot: Dot,
              winLength: Int): Pair<Boolean, Array<Cell>> {
        val winCells = Array(winLength) { Cell() }
        val isWin = checkColumn(index.first, field, dot, winLength, winCells) ||
                checkRow(index.second, field, dot, winLength, winCells) ||
                checkDiagonalsFromLeftToRight(index.first, index.second, field, dot, winLength, winCells) ||
                checkDiagonalsFromRightToLeft(index.first, index.second, field, dot, winLength, winCells)
        return Pair(isWin, winCells)
    }

    fun findAiStep(gameMode: String, field: Array<Array<Cell>>, winLength: Int): Pair<Int, Int> {
        return when (gameMode) {
            GameMode.PvA_Lazy -> findLazyAiStep(field)
            GameMode.PvA_Hard -> findHardAiStep(field, winLength)
            else -> findLazyAiStep(field)
        }
    }

    fun isFieldFull(field: Array<Array<Cell>>): Boolean {
        for (cells in field) {
            for (cell in cells) {
                if (cell.isEmpty) return false
            }
        }
        return true
    }

    private fun checkColumn(x: Int, field: Array<Array<Cell>>, dot: Dot, winLength: Int,
                            winCells: Array<Cell>): Boolean {
        var count = 0
        for (y in field.indices) {
            if (field.size == winLength && field[x][y].dot != dot) return false

            if (field[x][y].dot == dot) {
                winCells[count] = field[x][y]
                count++
                if (count == winLength) break
            } else {
                count = 0
            }
        }

        winLine = Win.VERTICAL
        return count == winLength
    }

    private fun checkRow(y: Int, field: Array<Array<Cell>>, dot: Dot, winLength: Int,
                         winCells: Array<Cell>): Boolean {
        var count = 0
        for (x in field.indices) {
            if (field.size == winLength && field[x][y].dot != dot) return false

            if (field[x][y].dot == dot) {
                winCells[count] = field[x][y]
                count++
                if (count == winLength) break
            } else {
                count = 0
            }
        }
        winLine = Win.HORIZONTAL
        return count == winLength
    }

    //    \ direction, all diagonals
    private fun checkDiagonalsFromLeftToRight(x: Int, y: Int, field: Array<Array<Cell>>, dot: Dot,
                                              winLength: Int, winCells: Array<Cell>): Boolean {
        var count = 0

        // up from click
        for (i in 0 until winLength) {
            if (field.size == winLength && field[i][i].dot != dot) return false
            if (x - i < 0 || y - i < 0) break

            if (field[x - i][y - i].dot == dot) {
                winCells[count] = field[x - i][y - i]
                count++
            } else {
                break
            }
        }

        // down from click
        for (i in 1 until winLength) {
            if (x + i >= field.size || y + i >= field.size) break

            if (field[x + i][y + i].dot == dot) {
                winCells[count] = field[x + i][y + i]
                count++
            } else {
                break
            }
        }
        winLine = Win.DIAGONAL_LEFT
        return count == winLength
    }

    //   / direction, all diagonals
    private fun checkDiagonalsFromRightToLeft(x: Int, y: Int, field: Array<Array<Cell>>, dot: Dot,
                                              winLength: Int, winCells: Array<Cell>): Boolean {
        var count = 0

        // up from click
        for (i in 0 until winLength) {
            if (field.size == winLength && field[winLength - (i + 1)][i].dot != dot) return false
            if (x + i >= field.size || y - i < 0) break

            if (field[x + i][y - i].dot == dot) {
                winCells[count] = field[x + i][y - i]
                count++
            } else {
                break
            }
        }

        // down from click
        for (i in 1 until winLength) {
            if (x - i < 0 || y + i >= field.size) break

            if (field[x - i][y + i].dot == dot) {
                winCells[count] = field[x - i][y + i]
                count++
            } else {
                break
            }
        }
        winLine = Win.DIAGONAL_RIGHT
        return count == winLength
    }

    private fun findHardAiStep(field: Array<Array<Cell>>, winLength: Int): Pair<Int, Int> {
        // если поставить 0 == выигрыш
        for ((x, cells) in field.withIndex()) {
            for ((y, cell) in cells.withIndex()) {
                if (isCellValid(cell)) {
                    cell.dot = Dot.O
                    if (isWin(Pair(x, y), field, cell.dot, winLength).first) {
                        cell.dot = Dot.EMPTY
                        return Pair(x, y)
                    } else {
                        cell.dot = Dot.EMPTY
                    }
                }
            }
        }

        // если игрок поставит Х и выиграет
        for ((x, cells) in field.withIndex()) {
            for ((y, cell) in cells.withIndex()) {
                if (isCellValid(cell)) {
                    cell.dot = Dot.X
                    if (isWin(Pair(x, y), field, cell.dot, winLength).first) {
                        cell.dot = Dot.EMPTY
                        return Pair(x, y)
                    } else {
                        cell.dot = Dot.EMPTY
                    }
                }
            }
        }

        // рандом
        return findLazyAiStep(field)
    }

    private fun findLazyAiStep(field: Array<Array<Cell>>): Pair<Int, Int> {
        val random = Random()
        var x: Int
        var y: Int

        do {
            x = random.nextInt(field.size)
            y = random.nextInt(field.size)
        } while (!isCellValid(field[x][y]))

        return Pair(x, y)
    }

    private fun isCellValid(cell: Cell): Boolean {
        return cell.dot == Dot.EMPTY
    }
}