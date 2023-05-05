package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class Video(
    val response: ResponseVideo,
)

data class ResponseVideo(
    val items: List<ItemVideo>
)

data class ItemVideo(
    @SerializedName("title")
    val title: String,

    @SerializedName("duration")
    val duration: Long,

    @SerializedName("adding_date")
    val addingDate: Long,

    @SerializedName("views")
    val views: String,

    val image: List<Image>
)

data class Image(
    @SerializedName("url")
    val url: String
)
