package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class Friends(
    val response: ResponseFriends
)

data class ResponseFriends(
    val items: List<Item>
)

data class Item(
    @SerializedName("photo_100")
    val photo100: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String
)