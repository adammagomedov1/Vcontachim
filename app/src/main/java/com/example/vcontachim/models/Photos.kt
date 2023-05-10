package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Photos(
    val response: ResponsePhotos,
)

data class ResponsePhotos(
    val items: List<ItemPhotos>,
)

data class ItemPhotos(
    val sizes: List<Size>,
    val likes: Likes,
    val comments: Comments,
    val reposts: Reposts
) : Serializable {

    data class Size(
        @SerializedName("url")
        val url: String
    ) : Serializable

    data class Likes(
        @SerializedName("count")
        val count: String
    ) : Serializable

    data class Comments(
        @SerializedName("count")
        val count: String
    ) : Serializable

    data class Reposts(
        @SerializedName("count")
        val count: String
    ) : Serializable
}