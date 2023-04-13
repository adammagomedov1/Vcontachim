package com.example.vcontachim.fragment

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

class VcontachimApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        cicerone = Cicerone.create()
        router = cicerone.router
        navigatorHolder = cicerone.getNavigatorHolder()
    }

    companion object {
        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigatorHolder: NavigatorHolder
    }
}