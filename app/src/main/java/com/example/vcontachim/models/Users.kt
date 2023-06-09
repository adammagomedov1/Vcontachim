package com.example.vcontachim.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Users(
    val response: List<Response>,
)

data class Response(
    val id: Long,
    @SerializedName("mobile_phone")
    val mobilePhone: String,

    @SerializedName("photo_200")
    val photo200: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,
) : Serializable