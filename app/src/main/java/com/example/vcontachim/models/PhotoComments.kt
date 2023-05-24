package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PhotoComments(
    val response: ResponsePhotoComments,
)

data class ResponsePhotoComments(
    val count: Long,
    val items: List<ItemPhotoComments>,
    val profiles: List<Profile>
)

data class ItemPhotoComments(
    val id: Long,
    @SerializedName("text")
    val textComments: String,
)

data class Profile(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("photo_200")
    val photo: String
) : Serializable