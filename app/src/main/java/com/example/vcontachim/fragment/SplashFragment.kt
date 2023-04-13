package com.example.vcontachim.fragment

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

        lifecycleScope.launch {
            delay(timeMillis = 3000)
            navigateAccountLoginScreen()
        }
    }

    private fun navigateAccountLoginScreen() {
        VcontachimApplication.router.replaceScreen(Screens.accountLoginScreen())
    }
}