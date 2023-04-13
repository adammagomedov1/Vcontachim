package com.example.vcontachim

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.vcontachim.fragment.VcontachimApplication
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.util.Scanner

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(this, R.id.fragment_main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToTheLoginScreen()
    }

    private fun goToTheLoginScreen() {
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