package com.foxy.tictactoe

import android.app.Application
import com.foxy.tictactoe.di.AppModule
import com.foxy.tictactoe.di.Scopes
import toothpick.Toothpick

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Toothpick.openScope(Scopes.APP).installModules(AppModule(this))
    }
}