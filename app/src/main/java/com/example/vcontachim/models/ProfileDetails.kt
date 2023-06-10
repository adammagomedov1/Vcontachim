package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileDetail(
    val response: List<ResponseProfileDetail>,
)

data class ResponseProfileDetail(
    val id: Long,
    val city: City?,
    val status: String,
    @SerializedName("followers_count")
    val followersCount: Long?,
    val career: List<Career>?,
    @SerializedName("can_send_friend_request")
    val canSendFriendRequest: Int,
    @SerializedName("photo_200")
    val photo200: String?,
    val counters: Counters,
    val online: Int,
    @SerializedName("is_friend")
    var isFriend: Int,
    val verified: Int,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
) : Serializable

data class City(
    val id: Long?,
    val title: String?,
) : Serializable

data class Career(
    val company: String?,
    val position: String?
) : Serializable

data class Counters(
    val followers: Long,
    val friends: Long,
) : Serializable