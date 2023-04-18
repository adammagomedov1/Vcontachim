package com.example.vcontachim.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
            "vcontachim",
            Context.MODE_PRIVATE
        )

        val editor: String? = sharedPreferences.getString("auth", null)

        lifecycleScope.launch {
            delay(timeMillis = 3000)

            if (editor == null) {
                navigateAccountLoginScreen()
            } else {
                navigateAccountLoginScreen1()
            }
        }
    }

    private fun navigateAccountLoginScreen() {
        VcontachimApplication.router.replaceScreen(Screens.accountLoginScreen())
    }

    private fun navigateAccountLoginScreen1() {
        VcontachimApplication.router.replaceScreen(Screens.main())
    }
}