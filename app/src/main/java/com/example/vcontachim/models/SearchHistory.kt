package com.example.vcontachim.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "SearchHistory")
data class SearchHistory(
    @PrimaryKey
    @SerializedName("searchHistory")
    val searchHistory: String,
)
