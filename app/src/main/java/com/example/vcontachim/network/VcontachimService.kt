package com.example.vcontachim.network

import android.content.SharedPreferences
import com.example.vcontachim.models.Users
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface VcontachimService {
    @GET("users.get")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("v") v: Double,
        @Query("fields") fields: String
    ): Users

}