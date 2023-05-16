package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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

    @SerializedName("date")
    val date: Long,

    @SerializedName("views")
    val views: String,

    val id: Long,

    val image: List<Image>
) : Serializable

data class Image(
    @SerializedName("url")
    val url: String
)
