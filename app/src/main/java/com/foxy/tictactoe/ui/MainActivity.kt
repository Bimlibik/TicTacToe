package com.foxy.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foxy.tictactoe.R
import kotlinx.android.synthetic.main.activity_container.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        setupToolbar()
    }

    private fun setupToolbar() {
        (this as AppCompatActivity).setSupportActionBar(toolbar)
        (this as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
