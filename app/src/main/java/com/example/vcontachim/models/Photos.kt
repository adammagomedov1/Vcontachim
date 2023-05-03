package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class Photos(
    val response: ResponsePhotos,
)

data class ResponsePhotos(
    val items: List<ItemPhotos>,
)

data class ItemPhotos(
    val sizes: List<Size>,
)

data class Size(
    @SerializedName("url")
    val url: String,
)
