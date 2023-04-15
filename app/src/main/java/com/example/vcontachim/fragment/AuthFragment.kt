package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.vcontachim.R
import com.example.vcontachim.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {

    val authUrl = "https://oauth.vk.com/authorize?" +
            "client_id=51611026" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&scope=12" +
            "&display=mobile" +
            "&response_type=token"

    private var binding: FragmentAuthBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        binding!!.idWebView.loadUrl(authUrl)
        binding!!.idWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url: Uri = request.url
                val urlString = url.toString()
                val urlDecoded = Uri.decode(urlString)
                val finalUrlDecoded = Uri.decode(urlDecoded)
                if (finalUrlDecoded.contains("access_token")){
                    val afterAccessToken = finalUrlDecoded.substringAfter("access_token=")
                    val beforeAccessToken = afterAccessToken.substringBefore("&")

                    val sharedPreferences = VcontachimApplication.context.getSharedPreferences(
                        "auth",
                        Context.MODE_PRIVATE
                    )

                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("auth", beforeAccessToken)
                    editor.apply()

                }
                return super.shouldOverrideUrlLoading(view, request)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}