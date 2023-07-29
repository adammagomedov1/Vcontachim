package com.example.vcontachim

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.vcontachim.network.InterceptorToken
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

        roomDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "vcontachim_database"
        )
            .allowMainThreadQueries()
            .build()

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(InterceptorToken())
            .addInterceptor(ChuckerInterceptor(context))
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
        lateinit var roomDatabase: AppDatabase
        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigatorHolder: NavigatorHolder
    }
}