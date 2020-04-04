package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.foxy.tictactoe.R
import com.foxy.tictactoe.data.Cell
import com.foxy.tictactoe.mvp.presenter.FieldPresenter
import com.foxy.tictactoe.mvp.view.FieldView
import com.foxy.tictactoe.utils.Dot
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

        setupToolbar()
        field.setupFieldCallback(this)
        field.setCellCount(presenter.getCellCount())
        btn_replay.setOnClickListener { presenter.reset() }
        btn_exit.setOnClickListener { openStartFragment() }
    }

    override fun drawDot(newField: MutableList<Cell>) {
        field.changeDotInCell(newField)
    }

    override fun openStartFragment() {
        val action = FieldFragmentDirections.actionStartFragment()
        findNavController().navigate(action)
    }

    override fun showWinner(centerX1: Float, centerY1: Float, centerX2: Float, centerY2: Float, dot: Dot) {
        field.animateWin(centerX1, centerY1, centerX2, centerY2)
        field.isEnabled = false
        layout_btn.visibility = View.VISIBLE

        tv_win_info.apply {
            visibility = View.VISIBLE
            text = getString(R.string.tv_win_info, dot.toString())
        }
    }

    override fun showTie() {
        layout_btn.visibility = View.VISIBLE
        field.isEnabled = false
        tv_win_info.apply {
            visibility = View.VISIBLE
            text = getString(R.string.tv_info)
        }
    }

    override fun replay() {
        field.reset()
        layout_btn.visibility = View.GONE
        tv_win_info.visibility = View.GONE
    }

    // Field callbacks
    override fun saveFieldSize(size: Int) {
        presenter.setFieldSize(size)
    }

    override fun saveCurrentCellIndex(x: Int, y: Int) {
        presenter.saveCellIndex(x, y)
    }

    override fun onCellClick(x: Int, y: Int) {
        presenter.checkFinalCellIndex(x, y)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
    }
}