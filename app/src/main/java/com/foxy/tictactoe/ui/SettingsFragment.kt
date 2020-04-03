package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat

import com.foxy.tictactoe.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        setupToolbar()
    }

    private fun returnToStartFragment() {
        val action = SettingsFragmentDirections.actionStartFragment()
        findNavController().navigate(action)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
        toolbar.title = getString(R.string.toolbar_title_settings)
        toolbar.setNavigationOnClickListener { returnToStartFragment()}
    }

}
