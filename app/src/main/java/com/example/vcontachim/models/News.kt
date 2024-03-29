package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class News(
    val response: ResponseNews,
)

data class ResponseNews(
    val items: List<ItemNews>,
    val groups: List<Group>,
    val profiles: List<ProfileNews>,
)

data class ItemNews(
    val id: Long,
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
)

data class Comments(
    val count: Long,
)

data class Attachment(
    val type: String,
    val photo: Photo?,
)

data class Photo(
    @SerializedName("owner_id")
    val ownerId: Long,
    val sizes: List<Size>,
)

data class Size(
    val url: String,
)

data class LikesNews(
    val count: Long,
    @SerializedName("user_likes")
    val userLikes: Long,
)

data class RepostsNews(
    val count: Long,
)

data class Views(
    val count: Long,
)

data class Group(
    val id: Long,
    val name: String,
    val verified: Int,
    @SerializedName("photo_200")
    val photo200: String,
)

data class ProfileNews(
    val id: Long,
    val screenName: String,
    val verified: Int,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
)