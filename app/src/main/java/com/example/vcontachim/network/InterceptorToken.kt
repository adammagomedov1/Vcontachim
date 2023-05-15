package com.example.vcontachim.network

import com.example.vcontachim.VcontachimApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class InterceptorToken: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${VcontachimApplication.sharedPreferencesHelper.tookToken}")
            .build()
        return chain.proceed(request)
    }
}