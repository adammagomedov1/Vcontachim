package com.example.vcontachim.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "SearchHistory")
data class SearchHistory(
    @PrimaryKey
    val searchHistory: String,
) : Serializable
