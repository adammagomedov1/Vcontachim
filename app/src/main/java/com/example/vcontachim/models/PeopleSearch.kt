package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

data class PeopleSearch(
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
)

data class ProfileSearchScreen(
    val id: Long,
    @SerializedName("photo_max")
    val photoMax: String,
    val city: CitySearchScreen?,
    val online: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val bdate: String?,
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    @SerializedName("is_friend")
    val isFriend: Int,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("track_code")
    val trackCode: String,
)

data class CitySearchScreen(
    val id: Long,
    val title: String,
)
