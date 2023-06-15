package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class News(
    val response: ResponseNews,
) : Serializable

data class ResponseNews(
    val items: List<ItemNews>,
    val groups: List<Group>,
) : Serializable

data class ItemNews(
    val type: String,
    val date: Long,
    val comments: Comments,
    val attachments: List<Attachment>,
    val likes: LikesNews,
    @SerializedName("source_id")
    val sourceId: Long,
    @SerializedName("post_source")
    val reposts: RepostsNews,
    val text: String,
    val views: Views,
) : Serializable
//
data class Comments(
    val count: Long,
) : Serializable

data class Attachment(
    val photo: Photo,
    val video: VideoNews
)

data class Photo(
    @SerializedName("owner_id")
    val ownerId: Long,
    val sizes: List<Size>,
) : Serializable

data class VideoNews(
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("first_frame")
    val firstFrame: List<FirstFrame>,
): Serializable

data class FirstFrame(
    val url: String,
): Serializable

data class Size(
    val url: String,
) : Serializable

data class LikesNews(
    val count: Long,
    @SerializedName("user_likes")
    val userLikes: Long,
) : Serializable

data class RepostsNews(
    val count: Long,
) : Serializable

data class Views(
    val count: Long,
) : Serializable

data class Group(
    val id: Long,
    val name: String,
    @SerializedName("photo_200")
    val photo200: String,
) : Serializable