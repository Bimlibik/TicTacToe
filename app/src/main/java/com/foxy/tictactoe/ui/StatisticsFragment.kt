package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.foxy.tictactoe.R
import com.foxy.tictactoe.utils.getPvAHardStatistics
import com.foxy.tictactoe.utils.getPvALazyStatistics
import com.foxy.tictactoe.utils.getPvPStatistics
import kotlinx.android.synthetic.main.fragment_statistics.*
import moxy.MvpAppCompatFragment

class StatisticsFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupStatistics()
    }

    private fun setupStatistics() {
        val pvp = getPvPStatistics()
        val pvaLazy = getPvALazyStatistics()
        val pvaHard = getPvAHardStatistics()

        tv_pvp_x.text = pvp.first.toString()
        tv_pvp_o.text = pvp.second.toString()
        tv_pva_lazy_x.text = pvaLazy.first.toString()
        tv_pva_lazy_o.text = pvaLazy.second.toString()
        tv_pva_hard_x.text = pvaHard.first.toString()
        tv_pva_hard_o.text = pvaHard.second.toString()
    }

    private fun returnToStartFragment() {
        val action = StartFragmentDirections.actionStartFragment()
        findNavController().navigate(action)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
        toolbar.title = getString(R.string.toolbar_title_statistics)
        toolbar.setNavigationOnClickListener { returnToStartFragment() }
    }
}