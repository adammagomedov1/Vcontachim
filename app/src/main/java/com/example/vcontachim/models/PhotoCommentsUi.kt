package com.example.vcontachim.models

import java.io.Serializable

data class PhotoCommentsUi(
    val fromId: Long,
    val textComments: String,
    val dateComments: Long,
    val firstName: String,
    val lastName: String,
    val photo: String,
    val id: Long,
    val personOnline: Boolean,
    val idComment: Int,
    val userLikes: Int,
) : Serializable
