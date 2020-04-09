package com.foxy.tictactoe.mvp.presenter

import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.data.GameRepository
import com.foxy.tictactoe.di.Scopes
import com.foxy.tictactoe.mvp.view.FieldView
import com.foxy.tictactoe.utils.*
import moxy.InjectViewState
import moxy.MvpPresenter
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class FieldPresenter : MvpPresenter<FieldView>() {

    @Inject
    lateinit var gameManager: GameManager

    @Inject
    lateinit var repository: GameRepository
    private lateinit var field: Array<Array<Cell>>

    private var gameMode = ""
    private var size = 0
    private var cellCount = 3
    private var playerX = true
    private var hasWin = false
    private var currentCellIndex = Pair(0, 0)

    init {
        val scope = Toothpick.openScope(Scopes.APP)
        Toothpick.inject(this, scope)
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        cellCount = repository.getFieldSize()
        gameMode = repository.getGameMode()
        field = Array(cellCount) { Array(cellCount) { Cell() } }
    }

    fun reset() {
        field = Array(cellCount) { Array(cellCount) { Cell() } }
        playerX = true
        hasWin = false
        initField()
        viewState.replay()
    }

    fun checkFinalCellIndex(x: Int, y: Int) {
        val finalCellIndex = getCellIndex(x, y)
        if (finalCellIndex.first < 0 || finalCellIndex.second < 0) return

        if (finalCellIndex == currentCellIndex) {
            makeStep(finalCellIndex)
        }
    }

    fun saveCellIndex(x: Int, y: Int) {
        currentCellIndex = getCellIndex(x, y)
    }

    fun getCellCount(): Int = repository.getFieldSize()

    fun setFieldSize(size: Int) {
        this.size = size
        initField()
    }

    private fun makeStep(index: Pair<Int, Int>) {
        val cell = field[index.first][index.second]
        val dot = gameManager.getDot(cell, playerX)
        if (dot == Dot.EMPTY) return

        when (gameMode) {
            GameMode.PvP -> checkPlayerStep(index)
            GameMode.PvA_Lazy, GameMode.PvA_Hard -> {
                if (playerX) {
                    checkPlayerStep(index)
                    checkAiStep()
                }
            }
        }
    }

    private fun checkAiStep() {
        if (hasWin) return
        if (gameManager.isFieldFull(field)) return

        val index = gameManager.findAiStep(gameMode, field, cellCount)
        if (index.first < 0 || index.second < 0) return

        changeDotInCell(index)
    }

    private fun checkPlayerStep(index: Pair<Int, Int>) {
        changeDotInCell(index)
    }

    private fun changeDotInCell(index: Pair<Int, Int>) {
        val cell = field[index.first][index.second]
        val dot = gameManager.getDot(cell, playerX)
        if (dot == Dot.EMPTY) return

        cell.dot = dot
        viewState.drawDot(getFieldWithDot())
        checkWin(index, dot)
    }

    private fun checkWin(index: Pair<Int, Int>, dot: Dot) {
        val winInfo = gameManager.isWin(index, field, dot, cellCount)
        if (winInfo.first) {
            hasWin = winInfo.first
            calculateCoordinatesForAnimation(winInfo.second)
            saveStatistics()
            return
        }

        playerX = !playerX

        if (gameManager.isFieldFull(field)) {
            viewState.showTie()
        }
    }

    private fun calculateCoordinatesForAnimation(winCells: Array<Cell>) {
        val halfCellSize = (size / cellCount) / 2
        val start = gameManager.getStartWinCoordinates(winCells, halfCellSize)
        val end = gameManager.getEndWinCoordinates(winCells, halfCellSize)
        viewState.showWinner(start.first, start.second, end.first, end.second, winCells.first().dot)
    }

    private fun saveStatistics() {
        repository.saveStatistics(gameMode, playerX)
    }

    private fun getFieldWithDot(): MutableList<Cell> {
        val newField = mutableListOf<Cell>()
        for (cells in field) {
            for (cell in cells) {
                if (cell.isEmpty) {
                    continue
                } else {
                    newField.add(cell)
                }
            }
        }
        return newField
    }

    private fun getCellIndex(x: Int, y: Int): Pair<Int, Int> {
        field.forEachIndexed { i, cells ->
            for ((j, cell) in cells.withIndex()) {
                if (cell.contains(x, y)) return Pair(i, j)
            }
        }
        return Pair(-1, -1)
    }

    private fun initField() {
        cellCount = repository.getFieldSize()
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