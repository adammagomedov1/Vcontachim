package com.example.vcontachim.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.vcontachim.Screens
import com.example.vcontachim.VcontachimApplication

class AuthViewModel : ViewModel() {

    fun loadAuth(
        url: Uri
    ) {
        val urlString = url.toString()
        val urlDecoded = Uri.decode(urlString)
        val finalUrlDecoded = Uri.decode(urlDecoded)
        if (finalUrlDecoded.contains("access_token")) {
            val afterAccessToken = finalUrlDecoded.substringAfter("access_token=")
            val beforeAccessToken = afterAccessToken.substringBefore("&")

            VcontachimApplication.sharedPreferencesHelper.load(beforeAccessToken)

            VcontachimApplication.router.replaceScreen(Screens.main())
        }
    }
}