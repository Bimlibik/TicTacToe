package com.foxy.tictactoe.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.foxy.tictactoe.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        setupToolbar()
        changeWinLengthMaxValue()
    }

    private fun changeWinLengthMaxValue() {
        val fieldSize = findPreference<SeekBarPreference>("prefs_field_size")
        val winLength = findPreference<SeekBarPreference>("prefs_win_length")
        fieldSize?.setOnPreferenceChangeListener { preference, newValue ->
            Log.i("TAG", "field seekbar click")
            if (winLength != null) {
                winLength.max = newValue as Int
            }
            
            true
        }
        (preferenceScreen as BaseAdapter).notifyDataSetChanged()
    }

    private fun returnToStartFragment() {
        val action = SettingsFragmentDirections.actionStartFragment()
        findNavController().navigate(action)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
        toolbar.title = getString(R.string.toolbar_title_settings)
        toolbar.setNavigationOnClickListener { returnToStartFragment() }
    }

}
