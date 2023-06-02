package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class VideoComment(
    val response: ResponseVideoComment,
)

data class ResponseVideoComment(
    val count: Long,
    val items: List<ItemVideoComment>,
    val profiles: List<ProfileVideoComment>,
)

data class ItemVideoComment(
    val id: Long,
    @SerializedName("from_id")
    val fromId: Long,
    val date: Long,
    val text: String,
    val likes: LikesVideoComment
)

data class LikesVideoComment(
    @SerializedName("user_likes")
    val userLikes: Int
)

data class ProfileVideoComment(
    val id: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    @SerializedName("is_closed")
    val isClosed: Boolean,
)
