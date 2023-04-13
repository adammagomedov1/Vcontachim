package com.example.vcontachim

import com.example.vcontachim.fragment.AccountLoginScreenFragment
import com.example.vcontachim.fragment.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun splash() = FragmentScreen { SplashFragment() }

    fun accountLoginScreen() = FragmentScreen { AccountLoginScreenFragment() }
}