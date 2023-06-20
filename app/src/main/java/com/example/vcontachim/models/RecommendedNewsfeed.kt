package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class RecommendedNewsfeed(
    val response: ResponseRecommendedNews,
)

data class ResponseRecommendedNews(
    val items: List<ItemRecommendedNews>,
    val profiles: List<ProfileRecommendedNews>,
    val groups: List<GroupRecommendedNews>,
)

data class ItemRecommendedNews(
    val type: String,
    @SerializedName("source_id")
    val sourceId: Long,
    val date: Long,
    val comments: CommentsRecommendedNews,
    val attachments: List<AttachmentRecommendedNews>,
    val id: Long,
    val likes: LikesRecommendedNews,
    @SerializedName("post_id")
    val postId: Long,
    val reposts: RepostsRecommendedNews,
    val text: String,
    val views: ViewsRecommendedNews,
)

data class CommentsRecommendedNews(
    val count: Long,
)

data class AttachmentRecommendedNews(
    val type: String,
    val photo: PhotoRecommendedNews?,
)

data class PhotoRecommendedNews(
    val id: Long,
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("post_id")
    val postId: Long?,
    val sizes: List<SizeRecommendedNews>,
    val text: String
)

data class SizeRecommendedNews(
    val url: String,
)

data class LikesRecommendedNews(
    @SerializedName("can_like")
    val canLike: Long,
    val count: Long,
    @SerializedName("user_likes")
    val userLikes: Long,
)

data class RepostsRecommendedNews(
    val count: Long,
)

data class ViewsRecommendedNews(
    val count: Long,
)

data class ProfileRecommendedNews(
    val id: Long,
    @SerializedName("photo_200")
    val photo200: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
)

data class GroupRecommendedNews(
    val id: Long,
    val name: String,
    @SerializedName("screen_name")
    val screenName: String,
    val type: String,
    @SerializedName("photo_200")
    val photo200: String,
)

