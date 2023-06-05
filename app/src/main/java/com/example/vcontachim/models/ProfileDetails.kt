package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileDetail(
    val response: List<ResponseProfileDetail>,
)

data class ResponseProfileDetail(
    val id: Long,
    val city: City,
    val status: String,
    val career: List<Career?>,
    @SerializedName("photo_200")
    val photo200: String,
    val online: Int,
    val verified: Int,
    val followersCount: Long,
    @SerializedName("friend_status")
    val friendStatus: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
) : Serializable

data class City(
    val id: Long,
    val title: String,
) : Serializable

data class Career(
    @SerializedName("city_id")
    val cityId: Long,
    @SerializedName("country_id")
    val countryId: Long,
    @SerializedName("group_id")
    val groupId: Long,
) : Serializable