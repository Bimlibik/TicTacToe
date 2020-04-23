package com.foxy.tictactoe.mvp.view

import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.utils.enums.Dot
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FieldView : MvpView {

    fun drawDot(newField: MutableList<Cell>)

    fun openStartFragment()

    fun showWinner(centerX1: Float, centerY1: Float, centerX2: Float, centerY2: Float, dot: Dot)

    fun showTie()

    fun replay()
}