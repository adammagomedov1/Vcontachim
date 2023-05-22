package com.example.vcontachim.models

class VideoLike(
    val response: ResponseLikesVideo
)

data class ResponseLikesVideo(
    val likes: Long
)
