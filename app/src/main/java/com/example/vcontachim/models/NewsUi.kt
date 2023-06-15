package com.example.vcontachim.models

import java.io.Serializable

data class NewsUi(
    val countComment: Long,
    val date: Long,
    val url: String,
    val sourceId: Long,
    val ownerId: Long,
    val countLike: Long,
    val countReposts: Long,
    val text: String,
    val countViews: Long,
    val name: String,
    val photo200: String,
    val userLikes: Long,
    val groupId: Long
) : Serializable
