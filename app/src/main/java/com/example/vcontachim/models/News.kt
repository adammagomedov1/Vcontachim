package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class News(
    val response: ResponseMain,
)

data class ResponseMain(
    val items: List<ItemMain>,
    val groups: List<Group>,
)

data class ItemMain(
    val date: Long,
    val comments: Comments,
    val attachments: List<Attachment>,
    val likes: LikesMain,
    @SerializedName("source_id")
    val sourceId: Long,
    @SerializedName("post_source")
    val reposts: RepostsMain,
    val text: String,
    val views: Views,
)

data class Comments(
    val count: Long,
)

data class Attachment(
    val photo: Photo,
)

data class Photo(
    @SerializedName("owner_id")
    val ownerId: Long,
    val sizes: List<Size>,
)

data class Size(
    val url: String,
)

data class LikesMain(
    val count: Long,
    @SerializedName("user_likes")
    val userLikes: Long,
)

data class RepostsMain(
    val count: Long,
)

data class Views(
    val count: Long,
)

data class Group(
    val id: Long,
    val name: String,
    @SerializedName("photo_200")
    val photo200: String,
)

