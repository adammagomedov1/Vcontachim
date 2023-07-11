package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class PhotoAlbums(
    val response: ResponsePhotoAlbums,
)

data class ResponsePhotoAlbums(
    val items: List<ItemPhotoAlbums>,
)

data class ItemPhotoAlbums(
    @SerializedName("size")
    val size: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("thumb_src")
    val thumbSrc: String,

    @SerializedName("id")
    val id: Long,

    val sizes: List<SizePhotoAlbums>,

    )

data class SizePhotoAlbums(
    val url: String,
    val src: String,
)
