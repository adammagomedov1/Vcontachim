package com.example.vcontachim.models

data class VideoCommentUi(
    val idComment: Long,
    val fromId: Long,
    val date: Long,
    val text: String,
    val userLikes: Int,
    val idProfile: Long,
    val firstName: String,
    val lastName: String,
)