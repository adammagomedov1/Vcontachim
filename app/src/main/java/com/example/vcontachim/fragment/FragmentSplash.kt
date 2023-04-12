package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSplash : Fragment(R.layout.splash_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(timeMillis = 3000)
            transition()
        }
    }

    fun transition() {
        VcontachimApplication.router.replaceScreen(Screens.name())
    }
}