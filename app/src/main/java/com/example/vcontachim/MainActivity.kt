package com.example.vcontachim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vcontachim.fragment.VcontachimApplication
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(this, R.id.fragment_main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // при перемоздпние икрана выполняется этот код
        if (savedInstanceState == null) {
            goToSplashScreen()
        }
    }

    private fun goToSplashScreen() {
        VcontachimApplication.router.replaceScreen(Screens.splash())
    }

    override fun onResume() {
        super.onResume()
        VcontachimApplication.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        VcontachimApplication.navigatorHolder.removeNavigator()
    }
}