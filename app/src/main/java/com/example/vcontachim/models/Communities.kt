package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName

class Communities(
    val response: ResponseCommunities
)

data class ResponseCommunities(
    val items: List<ItemCommunities>
)

data class ItemCommunities(
    @SerializedName("members_count")
    val membersCount: Long,

    @SerializedName("name")
    val screenName: String,

    @SerializedName("photo_200")
    val photo50: String,

    @SerializedName("verified")
    val verified: Long
)
