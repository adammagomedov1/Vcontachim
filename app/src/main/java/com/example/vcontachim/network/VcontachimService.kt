package com.example.vcontachim.network

import com.example.vcontachim.models.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface VcontachimService {
    @GET("users.get")
    suspend fun getUsers(
        @Query("fields") fields: String = "contacts,photo_200"
    ): Users

    @GET("friends.get")
    suspend fun getFriends(
        @Query("fields") fields: String = "photo_200"
    ): Friends

    @GET("groups.get")
    suspend fun getGroups(
        @Query("extended") extended: Int = 1,
        @Query("fields") fields: String = "members_count,verified",
    ): Communities

    @GET("photos.getAlbums")
    suspend fun getAlbumsPhotos(
        @Query("need_covers") needCovers: Int = 1,
    ): PhotoAlbums

    @GET("photos.get")
    suspend fun getPhotos(
        @Query("extended") extended: Int = 1,
        @Query("album_id") albumId: Long
    ): Photos

    @GET("video.get")
    suspend fun getVideo(
        @Query("extended") extended: Int = 1
    ): Video

    @POST("likes.add")
    suspend fun addLike(
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: String
    ): Likes

    @POST("likes.delete")
    suspend fun deleteLike(
        @Query("type") type: String = "photo",
        @Query("item_id") itemId: String
    ): Likes

    @POST("video.delete")
    suspend fun deleteVideo(
        @Query("video_id") videoId: Long
    )

    @GET("likes.add")
    suspend fun addLikeVideo(
        @Query("type") type: String = "video",
        @Query("item_id") itemId: Long,
        @Query("owner_id") ownerId: Long
    ): VideoLike

    @GET("likes.delete")
    suspend fun deleteLikeVideo(
        @Query("type") type: String = "video",
        @Query("item_id") itemId: Long,
        @Query("owner_id") ownerId: Long
    ): VideoLike

    @GET("photos.getComments")
    suspend fun getCommentsPhotos(
        @Query("photo_id") photoId: String,
        @Query("extended") extended: String = "profiles",
        @Query("fields") fields: String = "photo_200,online",
        @Query("need_likes") needLikes: Int = 1
    ): PhotoComments

    @GET("likes.add")
    suspend fun addLikeComment(
        @Query("type") type: String = "photo_comment",
        @Query("item_id") itemId: Int,
    )

    @GET("likes.delete")
    suspend fun deleteLikeComment(
        @Query("type") type: String = "photo_comment",
        @Query("item_id") itemId: Int,
    )

    @POST("photos.createComment")
    suspend fun createCommentPhotos(
        @Query("photo_id") photoId: String,
        @Query("message") message: String
    )
}