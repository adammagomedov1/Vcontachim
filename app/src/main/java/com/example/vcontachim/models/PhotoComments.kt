package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PhotoComments(
    val response: ResponsePhotoComments,
)

data class ResponsePhotoComments(
    val count: Long,
    val items: List<ItemPhotoComments>,
    val profiles: List<Profile>
)

data class ItemPhotoComments(
    val id: Int,
    @SerializedName("from_id")
    val fromId: Long,
    @SerializedName("text")
    val textComments: String,
    @SerializedName("date")
    val dateComments: Long,
    val likes: CommentLikes,
)

data class CommentLikes(
    @SerializedName("user_likes")
    val userLikes: Int,
)

data class Profile(
    val id: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("photo_200")
    val photo: String,
    @SerializedName("online")
    val personOnline: Int
) : Serializable