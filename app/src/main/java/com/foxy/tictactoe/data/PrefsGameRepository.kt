package com.foxy.tictactoe.data

import com.foxy.tictactoe.utils.*
import toothpick.InjectConstructor

@InjectConstructor
class  PrefsGameRepository : GameRepository {

    override fun getGameMode(): String {
        return getPrefsGameMode()
    }

    override fun getFieldSize(): Int {
        return getPrefsFieldSize()
    }

    override fun getStatistics(gameMode: String): Pair<Int, Int> {
        var statistics = Pair(0, 0)
        when(gameMode) {
            GameMode.PvP -> statistics = getPvPStatistics()
            GameMode.PvA_Lazy -> statistics = getPvALazyStatistics()
            GameMode.PvA_Hard -> statistics = getPvAHardStatistics()
        }
        return statistics
    }

    override fun saveStatistics(gameMode: String, playerX: Boolean) {
        when(gameMode) {
            GameMode.PvP -> savePvPStatistics(playerX)
            GameMode.PvA_Lazy -> savePvALazyStatistics(playerX)
            GameMode.PvA_Hard -> savePvAHardStatistics(playerX)
        }
    }

}