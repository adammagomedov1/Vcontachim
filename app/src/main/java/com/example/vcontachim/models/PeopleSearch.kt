package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class SearchScreen(
    val response: ResponseSearchScreen,
)

data class ResponseSearchScreen(
    val count: Long,
    val items: List<ItemSearchScreen>,
)

data class ItemSearchScreen(
    val description: String,
    val type: String,
    val profile: ProfileSearchScreen?,
    val group: Group?,
)

data class ProfileSearchScreen(
    val id: Long,
    @SerializedName("photo_max")
    val photoMax: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("track_code")
    val trackCode: String,
)
