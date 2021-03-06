package com.foxy.tictactoe.di

import android.content.Context
import com.foxy.tictactoe.data.PrefsGameRepository
import com.foxy.tictactoe.data.GameRepository
import com.foxy.tictactoe.utils.GameManager
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)

        // ?
        val gameManager = GameManager()
        bind(GameManager::class.java).toInstance(gameManager)

        bind(GameRepository::class.java)
            .to(PrefsGameRepository::class.java)
            .singleton()
    }
}