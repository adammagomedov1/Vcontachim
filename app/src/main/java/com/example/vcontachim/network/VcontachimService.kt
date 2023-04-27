package com.example.vcontachim.network

import com.example.vcontachim.models.Communities
import com.example.vcontachim.models.Friends
import com.example.vcontachim.models.Users
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VcontachimService {
    @GET("users.get")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "contacts,photo_200"
    ): Users

    @GET("friends.get")
    suspend fun getFriends(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "photo_200"
    ): Friends

    @GET("groups.get")
    suspend fun getGroups(
        @Header("Authorization") token: String,
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1,
        @Query("fields") fields: String = "members_count,verified",
    ): Communities
}