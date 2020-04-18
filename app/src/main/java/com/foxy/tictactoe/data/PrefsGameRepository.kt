package com.foxy.tictactoe.data

import com.foxy.tictactoe.utils.*
import com.foxy.tictactoe.utils.enums.GameMode
import toothpick.InjectConstructor

@InjectConstructor
class  PrefsGameRepository : GameRepository {

    override fun getGameMode(): String {
        return getGameModeFromPrefs()
    }

    override fun getFirstStep(): String {
        return getFirstStepFromPrefs()
    }

    override fun saveFirstStep(firstStep: String) {
        saveFirstStepToPrefs(firstStep)
    }

    override fun getFieldSize(): Int {
        return getFieldSizeFromPrefs()
    }

    override fun getWinLineLength(): Int {
        return getsWinLineLengthFromPrefs()
    }

    override fun saveWinLineLength(winLength: Int) {
        saveWinLineLengthToPrefs(winLength)
    }

    override fun getStatistics(gameMode: String): Pair<Int, Int> {
        var statistics = Pair(0, 0)
        when(gameMode) {
            GameMode.PvP -> statistics = getPvPStatisticsFromPrefs()
            GameMode.PvA_Lazy -> statistics = getPvALazyStatisticsFromPrefs()
            GameMode.PvA_Hard -> statistics = getPvAHardStatisticsFromPrefs()
        }
        return statistics
    }

    override fun saveStatistics(gameMode: String, playerX: Boolean, winPlayer: Boolean) {
        when(gameMode) {
            GameMode.PvP -> savePvPStatisticsToPrefs(playerX)
            GameMode.PvA_Lazy -> savePvALazyStatisticsToPrefs(winPlayer)
            GameMode.PvA_Hard -> savePvAHardStatisticsToPrefs(winPlayer)
        }
    }

}