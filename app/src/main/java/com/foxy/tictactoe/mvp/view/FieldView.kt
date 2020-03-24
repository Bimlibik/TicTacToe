package com.foxy.tictactoe.mvp.view

import com.foxy.tictactoe.data.Cell
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FieldView : MvpView {

    fun onCellClick(cellIndex: Pair<Int, Int>)

    fun drawDot(newField: MutableList<Cell>)

    fun openStartFragment()

    fun showWinner()

    fun replay()
}