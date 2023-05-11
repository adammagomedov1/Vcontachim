package com.example.vcontachim.models

class Likes(
    val response: ResponseLikes,
)

data class ResponseLikes(
    val likes: Long,
)