package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.foxy.tictactoe.R
import kotlinx.android.synthetic.main.fragment_start.*
import moxy.MvpAppCompatFragment

class StartFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        btn_play.setOnClickListener { startGame() }
        btn_settings.setOnClickListener { openSettings() }
        btn_statistics.setOnClickListener { openStatistics() }
    }

    private fun startGame() {
        val action = StartFragmentDirections.actionStartFragmentToFieldFragment()
        findNavController().navigate(action)
    }

    private fun openSettings() {
        val action = StartFragmentDirections.actionStartFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun openStatistics() {
        val action = StartFragmentDirections.actionStartFragmentToStatisticsFragment()
        findNavController().navigate(action)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
    }
}