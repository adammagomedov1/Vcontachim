package com.example.vcontachim.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vcontachim.R
import com.example.vcontachim.Screens
import com.example.vcontachim.databinding.FragmentAccountLoginScreenBinding
import com.example.vcontachim.VcontachimApplication

class AccountLoginScreenFragment : Fragment(R.layout.fragment_account_login_screen) {

    private var binding: FragmentAccountLoginScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountLoginScreenBinding.bind(view)

        binding!!.loginScreenButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                VcontachimApplication.router.replaceScreen(Screens.auth())
            }
        })
    }
}