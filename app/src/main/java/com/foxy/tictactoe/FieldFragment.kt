package com.foxy.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_field.*
import moxy.MvpAppCompatFragment

class FieldFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_field, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_replay.setOnClickListener { replay() }
        btn_exit.setOnClickListener { exit() }
    }

    private fun replay() {}

    private fun exit() {
        val action = FieldFragmentDirections.actionStartFragment()
        findNavController().navigate(action)
    }
}