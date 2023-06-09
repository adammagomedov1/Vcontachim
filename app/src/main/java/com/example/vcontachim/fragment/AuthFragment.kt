package com.example.vcontachim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.databinding.FragmentAuthBinding
import com.example.vcontachim.viewmodel.AuthViewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val authUrl = "https://oauth.vk.com/authorize?" +
            "client_id=51611026" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&scope=offline,photos,video,wall,friends,groups" +
            "&display=mobile" +
            "&response_type=token"

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private var binding: FragmentAuthBinding? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        binding!!.idWebView.loadUrl(authUrl)
        binding!!.idWebView.settings.javaScriptEnabled = true
        binding!!.idWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url = request.url
                viewModel.loadAuth(url)
                return super.shouldOverrideUrlLoading(view, request)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}