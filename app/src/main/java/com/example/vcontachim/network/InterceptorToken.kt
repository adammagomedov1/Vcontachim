package com.example.vcontachim.network

import com.example.vcontachim.VcontachimApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class InterceptorToken : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${VcontachimApplication.sharedPreferencesHelper.tookToken}"
            )
            .build()

        val url = request
            .url()
            .newBuilder()
            .addQueryParameter("v", 5.131.toString())
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}