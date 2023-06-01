package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Video(
    val response: ResponseVideo,
)

data class ResponseVideo(
    val items: List<ItemVideo>
)

data class ItemVideo(
    @SerializedName("title")
    val title: String,

    @SerializedName("duration")
    val duration: Long,

    @SerializedName("date")
    val date: Long,

    @SerializedName("likes")
    val likes: NumberLikes,

    val comments: Long,

    @SerializedName("views")
    val views: String,

    val player: String,

    val id: Long,

    @SerializedName("owner_id")
    val ownerId: Long,

    val reposts: Reposts,

    val image: List<Image>
) : Serializable

data class Image(
    @SerializedName("url")
    val url: String
) : Serializable

data class NumberLikes(
    @SerializedName("count")
    val countLikes: Long,

    @SerializedName("user_likes")
    val userLikes: Int

) : Serializable

data class Reposts(
    @SerializedName("count")
    val reposted: Long,
) : Serializable