package com.example.vcontachim.network

import com.example.vcontachim.models.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface VcontachimService {
    @GET("users.get")
    suspend fun getUsers(
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "contacts,photo_200"
    ): Users

    @GET("friends.get")
    suspend fun getFriends(
        @Query("v") v: Double = 5.131,
        @Query("fields") fields: String = "photo_200"
    ): Friends

    @GET("groups.get")
    suspend fun getGroups(
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1,
        @Query("fields") fields: String = "members_count,verified",
    ): Communities

    @GET("photos.getAlbums")
    suspend fun getAlbumsPhotos(
        @Query("v") v: Double = 5.131,
        @Query("need_covers") needCovers: Int = 1,
    ): PhotoAlbums

    @GET("photos.get")
    suspend fun getPhotos(
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1,
        @Query("album_id") albumId: Long
    ): Photos

    @GET("video.get")
    suspend fun getVideo(
        @Query("v") v: Double = 5.131,
        @Query("extended") extended: Int = 1
    ): Video

    @POST("likes.add")
    suspend fun addLike(
        @Query("v") v: Double = 5.131,
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: String
    ): Likes

    @POST("likes.delete")
    suspend fun deleteLike(
        @Query("v") v: Double = 5.131,
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: String
    ): Likes

    @POST("video.delete")
    suspend fun deleteVideo(
        @Query("v") v: Double = 5.131,
        @Query("video_id") videoId: Long
    )
}