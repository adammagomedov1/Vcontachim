package com.example.vcontachim

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.vcontachim.network.MyInterceptorTokena
import com.example.vcontachim.network.VcontachimService
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class VcontachimApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(MyInterceptorTokena())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        vcontachimService = retrofit.create()

        cicerone = Cicerone.create()
        router = cicerone.router
        navigatorHolder = cicerone.getNavigatorHolder()
        sharedPreferencesHelper = SharedPreferencesHelper()
    }

    companion object {
        lateinit var sharedPreferencesHelper: SharedPreferencesHelper

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        lateinit var vcontachimService: VcontachimService

        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigatorHolder: NavigatorHolder
    }
}