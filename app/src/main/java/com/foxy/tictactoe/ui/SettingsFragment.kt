package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.foxy.tictactoe.R
import com.foxy.tictactoe.data.GameRepository
import com.foxy.tictactoe.di.Scopes
import com.foxy.tictactoe.utils.enums.GameMode
import com.foxy.tictactoe.utils.enums.Step
import toothpick.Toothpick
import javax.inject.Inject


class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var repository: GameRepository

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val scope = Toothpick.openScope(Scopes.APP)
        Toothpick.inject(this, scope)

        addPreferencesFromResource(R.xml.preferences)
        setupToolbar()
        setupFieldSizeListener()
        setupGameMode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(resources.getColor(R.color.backgroundLight))
    }

    private fun setupGameMode() {
        val gameMode = findPreference<ListPreference>("prefs_game_mode")
        val firstStep = findPreference<ListPreference>("prefs_first_step")
        if (gameMode != null && firstStep != null) {
            checkGameMode(gameMode.value, firstStep)
            gameMode.setOnPreferenceChangeListener { _, newValue ->
                checkGameMode(newValue.toString(), firstStep)
                true
            }
        }
    }

    private fun checkGameMode(gameMode: String, firstStep: ListPreference) {
        if (gameMode == GameMode.PvP) {
            firstStep.value = Step.PLAYER
            firstStep.isEnabled = false
            repository.saveFirstStep(firstStep.value)
        } else {
            firstStep.isEnabled = true
        }
    }

    private fun setupFieldSizeListener() {
        val fieldSize = findPreference<SeekBarPreference>("prefs_field_size")
        val winLength = findPreference<SeekBarPreference>("prefs_win_length")
        if (winLength != null && fieldSize != null) {
            changeWinLength(fieldSize.value, winLength)
        }

        fieldSize?.setOnPreferenceChangeListener { _, newValue ->
            if (winLength != null) {
                changeWinLength(newValue as Int, winLength)
            }
            true
        }
    }

    private fun changeWinLength(value: Int, winLength: SeekBarPreference) {
        winLength.max = value

        if (winLength.value > value) {
            winLength.value = value
            repository.saveWinLineLength(winLength.value)
        }
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
