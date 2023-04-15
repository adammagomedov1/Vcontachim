package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.vcontachim.R
import com.example.vcontachim.Screens

class AccountLoginScreenFragment : Fragment(R.layout.fragmen_taccount_login_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.login_screen_button)
        button.setOnClickListener {
            VcontachimApplication.router.replaceScreen(Screens.auth())
        }

    }
}