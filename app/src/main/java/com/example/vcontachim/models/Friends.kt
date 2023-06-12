package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Friends(
    val response: ResponseFriends
)

data class ResponseFriends(
    val items: List<Item>
)

data class Item(
    val id: Long,
    @SerializedName("photo_200")
    val photo200: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String
) : Serializable