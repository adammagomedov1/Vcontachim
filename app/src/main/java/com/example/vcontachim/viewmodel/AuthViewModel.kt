package com.example.vcontachim.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.webkit.WebResourceRequest
import androidx.lifecycle.ViewModel
import com.example.vcontachim.fragment.VcontachimApplication

class AuthViewModel : ViewModel() {

    fun loadAuth(
        request: WebResourceRequest
    ) {
        val url: Uri = request.url
        val urlString = url.toString()
        val urlDecoded = Uri.decode(urlString)
        val finalUrlDecoded = Uri.decode(urlDecoded)
        if (finalUrlDecoded.contains("access_token")) {
            val afterAccessToken = finalUrlDecoded.substringAfter("access_token=")
            val beforeAccessToken = afterAccessToken.substringBefore("&")

            val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                "vcontachim_settings",
                Context.MODE_PRIVATE
            )

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("auth", beforeAccessToken)
            editor.apply()

        }
    }
}