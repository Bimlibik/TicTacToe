package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.foxy.tictactoe.R
import kotlinx.android.synthetic.main.fragment_start.*
import moxy.MvpAppCompatFragment

class StartFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_play.setOnClickListener { startGame() }
    }

    private fun startGame() {
        val action = StartFragmentDirections.actionStartFragmentToFieldFragment()
        findNavController().navigate(action)
    }
}