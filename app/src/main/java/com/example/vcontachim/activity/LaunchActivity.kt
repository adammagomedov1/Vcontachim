package com.example.vcontachim.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.VcontachimApplication
import com.github.terrakok.cicerone.androidx.AppNavigator

class LaunchActivity : AppCompatActivity(R.layout.activity_launch) {

    private val navigator = AppNavigator(this, R.id.fragment_main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // добавил условие для того чтобы экран повторно не открывался при повороте экрана
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