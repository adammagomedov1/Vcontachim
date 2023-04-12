package com.example.vcontachim

import com.example.vcontachim.fragment.FragmentName
import com.example.vcontachim.fragment.FragmentSplash
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun home() = FragmentScreen { FragmentSplash() }

    fun name() = FragmentScreen { FragmentName() }
}