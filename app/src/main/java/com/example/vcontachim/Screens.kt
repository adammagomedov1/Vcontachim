package com.example.vcontachim

import android.content.Intent
import com.example.vcontachim.activity.MainActivity
import com.example.vcontachim.fragment.*
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun splash() = FragmentScreen { SplashFragment() }

    fun accountLoginScreen() = FragmentScreen { AccountLoginScreenFragment() }

    fun auth() = FragmentScreen { AuthFragment() }

    fun main() = ActivityScreen { Intent(it, MainActivity::class.java) }

    fun mainScreen() = FragmentScreen { MainScreenFragment() }

    fun profileScreen() = FragmentScreen { ProfileFragment() }

}