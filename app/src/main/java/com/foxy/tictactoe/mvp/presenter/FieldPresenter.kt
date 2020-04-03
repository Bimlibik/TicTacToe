package com.foxy.tictactoe.mvp.presenter

import android.util.Log
import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.mvp.view.FieldView
import com.foxy.tictactoe.utils.Dot
import com.foxy.tictactoe.utils.GameManager
import com.foxy.tictactoe.utils.getFieldSize
import com.foxy.tictactoe.utils.getGameMode
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class FieldPresenter : MvpPresenter<FieldView>() {

    private val gameManager = GameManager()
    private var gameMode = ""
    private var size = 0
    private var cellCount = 3
    private var playerX = true
    private var currentCellIndex = Pair(0, 0)
    private lateinit var field: Array<Array<Cell>>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        cellCount = getFieldSize()
        gameMode = getGameMode()
        field = Array(cellCount) { Array(cellCount) { Cell() } }
    }

    fun reset() {
        field = Array(cellCount) { Array(cellCount) { Cell() } }
        playerX = true
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

    fun getCellCount() : Int = getFieldSize()

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
        }
    }

    private fun calculateCoordinatesForAnimation(winCells: Array<Cell>) {
        val halfCellSize = (size / cellCount) / 2
        val start = gameManager.getStartCoordinates(winCells, halfCellSize)
        val end = gameManager.getEndCoordinates(winCells, halfCellSize)
        viewState.showWinner(start.first, start.second, end.first, end.second, winCells.first().dot)
    }

    private fun getFieldWithDot() : MutableList<Cell> {
        val newField = mutableListOf<Cell>()
        for (map in field) {
            for (cell in map) {
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
        field.forEachIndexed { i, cells ->
            for ((j, cell) in cells.withIndex()) {
                if (cell.contains(x, y)) return Pair(i, j)
            }
        }
        return Pair(-1, -1)
    }

    private fun initField() {
        cellCount = getFieldSize()
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