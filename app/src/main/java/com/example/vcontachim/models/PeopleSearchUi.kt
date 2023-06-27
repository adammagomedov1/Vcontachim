package com.example.vcontachim.models

data class PeopleSearchUi(
    val maxPhoto: String,
    val id: Long,
    val firstName: String,
    val description: String,
    val cityTitle: String?,
    val bdate: String?,
    val isFriend: Int,
    val online: Int
)
