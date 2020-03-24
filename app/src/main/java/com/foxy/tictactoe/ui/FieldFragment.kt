package com.foxy.tictactoe.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.foxy.tictactoe.R
import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.mvp.presenter.FieldPresenter
import com.foxy.tictactoe.mvp.view.FieldView
import com.foxy.tictactoe.utils.FieldCallback
import kotlinx.android.synthetic.main.fragment_field.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class FieldFragment : MvpAppCompatFragment(), FieldView, FieldCallback {

    @InjectPresenter
    lateinit var presenter: FieldPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_field, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        field.setupFieldCallback(this)
        btn_replay.setOnClickListener { replay() }
        btn_exit.setOnClickListener { openStartFragment() }
    }

    override fun onCellClick(cellIndex: Pair<Int, Int>) {
        // TODO: check step
        Log.i("TAG", "cell index: $cellIndex")
    }

    override fun drawDot(newField: MutableList<Cell>) {
        field.changeDotInCell(newField)
        // TODO: change dot in cell
    }

    override fun openStartFragment() {
        val action = FieldFragmentDirections.actionStartFragment()
        findNavController().navigate(action)
    }

    override fun showWinner() {
        // TODO: show button layout and textView with winner
    }

    override fun replay() {
        // TODO: reset field
    }


    // Field callback
    override fun saveFieldSize(size: Int) {
        Log.i("TAG", "field size = $size")
        presenter.setFieldSize(size)
    }

    override fun saveCurrentCellIndex(x: Int, y: Int) {
        presenter.saveCellIndex(x, y)
    }

    override fun onCellClick(x: Int, y: Int) {
        presenter.checkFinalCellIndex(x, y)
    }
}