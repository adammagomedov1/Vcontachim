package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.VcontachimApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = VcontachimApplication.sharedPreferencesHelper

        val tookToken: String? = sharedPreferences.tookToken

        lifecycleScope.launch {
            delay(timeMillis = 3000)

            if (tookToken == null) {
                navigateAccountLoginScreen()
            } else {
                navigateMain()
            }
        }
    }

    private fun navigateAccountLoginScreen() {
        VcontachimApplication.router.replaceScreen(Screens.accountLoginScreen())
    }

    private fun navigateMain() {
        VcontachimApplication.router.replaceScreen(Screens.main())
    }
}