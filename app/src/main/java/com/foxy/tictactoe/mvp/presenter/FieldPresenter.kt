package com.foxy.tictactoe.mvp.presenter

import android.util.Log
import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.mvp.view.FieldView
import com.foxy.tictactoe.utils.Dot
import com.foxy.tictactoe.utils.GameManager
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class FieldPresenter : MvpPresenter<FieldView>() {

    private val gameManager = GameManager()
    private var size = 0
    private val cellCount = 3
    private var playerX = true
    private var currentCellIndex = Pair(0, 0)
    private var field = Array(cellCount) { Array(cellCount) { Cell() } }

    fun reset() {
        field = Array(cellCount) { Array(cellCount) { Cell() } }
        initField()
        viewState.replay()
    }

    fun checkFinalCellIndex(x: Int, y: Int) {
        val finalCellIndex = getCellIndex(x, y)
        if (finalCellIndex == currentCellIndex) {
            checkPlayerStep(finalCellIndex)
        }
    }

    fun saveCellIndex(x: Int, y: Int) {
        currentCellIndex = getCellIndex(x, y)
    }

    fun setFieldSize(size: Int) {
        this.size = size
        initField()
    }

    private fun checkPlayerStep(index: Pair<Int, Int>) {
        val cell = field[index.first][index.second]
        val dot = gameManager.getDot(cell, playerX)
        if (dot == Dot.EMPTY) {
            return
        }
        cell.dot = dot
        viewState.drawDot(getFieldWithDot())
        checkWin(index, dot)
    }

    private fun checkWin(index: Pair<Int, Int>, dot: Dot) {
        val winInfo = gameManager.isWin(index, field, dot, cellCount)
        if (winInfo.first) {
            calculateCoordinatesForAnimation(winInfo.second)
            return
        }

        playerX = !playerX

        if (gameManager.isFieldFull(field)) {
            viewState.showTie()
            Log.i("WIN", "game end with a tie")
        }
    }

    private fun calculateCoordinatesForAnimation(winCells: Array<Cell>) {
        val halfCellSize = (size / cellCount) / 2
        val centerX1 = (winCells.first().right - halfCellSize).toFloat()
        val centerY1 = (winCells.first().bottom - halfCellSize).toFloat()
        val centerX2 = (winCells.last().right - halfCellSize).toFloat()
        val centerY2 = (winCells.last().bottom - halfCellSize).toFloat()
        viewState.showWinner(centerX1, centerY1, centerX2, centerY2, winCells.first().dot)
    }

    private fun getFieldWithDot() : MutableList<Cell> {
        val newField = mutableListOf<Cell>()
        for ((x, map) in field.withIndex()) {
            for ((y, cell) in map.withIndex()) {
                if (cell.isEmpty) {
                    continue
                } else {
                    newField.add(cell)
                }
            }
        }
        return newField
    }

    private fun getCellIndex(x: Int, y: Int) : Pair<Int, Int> {
        field.forEachIndexed {i, cells ->
            for ((j, cell) in cells.withIndex()) {
                if (cell.contains(x, y)) return Pair(i, j)
            }
        }
        return Pair(-1, -1)
    }

    private fun initField() {
        val cellSize = size / cellCount
        for (y in 0 until cellCount) {
            for (x in 0 until cellCount) {
                field[x][y].apply {
                    left = x * cellSize
                    top = y * cellSize
                    right = (x + 1) * cellSize
                    bottom = (y + 1) * cellSize
                }
            }
        }
    }

}