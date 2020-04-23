package com.foxy.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.foxy.tictactoe.R
import com.foxy.tictactoe.data.GameRepository
import com.foxy.tictactoe.di.Scopes
import com.foxy.tictactoe.utils.enums.GameMode
import kotlinx.android.synthetic.main.fragment_statistics.*
import moxy.MvpAppCompatFragment
import toothpick.Toothpick
import javax.inject.Inject

class StatisticsFragment : MvpAppCompatFragment() {

    @Inject
    lateinit var repository: GameRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val scope = Toothpick.openScope(Scopes.APP)
        Toothpick.inject(this, scope)
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupStatistics()
        btn_reset.setOnClickListener { resetStatistics() }
    }

    private fun resetStatistics() {
        val dialog = AlertDialog.Builder(activity!!)
        dialog.apply {
            setTitle(getString(R.string.dialog_reset_title))
            setMessage(getString(R.string.dialog_reset_msg))
            setPositiveButton(getString(R.string.dialog_positive_btn)) { _, _ -> clearAllStatistics() }
            setNegativeButton(getString(R.string.dialog_negative_btn)) { dialogInterface, _ -> dialogInterface.dismiss() }
        }.show()
    }

    private fun clearAllStatistics() {
        repository.clearStatistics()
        setupStatistics()
    }

    private fun setupStatistics() {
        val pvp = repository.getStatistics(GameMode.PvP)
        val pvaLazy = repository.getStatistics(GameMode.PvA_Lazy)
        val pvaHard = repository.getStatistics(GameMode.PvA_Hard)

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