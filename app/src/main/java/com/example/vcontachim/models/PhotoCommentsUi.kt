package com.example.vcontachim.models

data class PhotoCommentsUi(
    val fromId: Long,

    val textComments: String,

    val dateComments: Long,

    val firstName: String,

    val lastName: String,

    val photo: String,

    val id: Long,

    val personOnline: Boolean
)
