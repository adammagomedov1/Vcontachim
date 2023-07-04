package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

data class PeopleSearch(
    val response: ResponseSearch,
)

data class ResponseSearch(
    val count: Long,
    val items: List<ItemSearch>,
)

data class ItemSearch(
    val description: String,
    val type: String,
    val profile: ProfileSearch?,
)

data class ProfileSearch(
    val id: Long,
    @SerializedName("photo_max")
    val photoMax: String,
    val city: CitySearch?,
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

data class CitySearch(
    val id: Long,
    val title: String,
)
