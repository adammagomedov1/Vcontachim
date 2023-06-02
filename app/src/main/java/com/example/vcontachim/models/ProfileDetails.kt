package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class ProfileDetail(
    val response: List<ResponseProfileDetail>,
)

data class ResponseProfileDetail(
    val id: Long,
    val city: City,
    val status: String,
    val career: List<Any?>,
    @SerializedName("photo_100")
    val photo100: String,
    val online: Long,
    val verified: Long,
    @SerializedName("friend_status")
    val friendStatus: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    @SerializedName("is_closed")
    val isClosed: Boolean,
)

data class City(
    val id: Long,
    val title: String,
)
