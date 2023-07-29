package com.example.vcontachim.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.vcontachim.models.SearchHistory

@Dao
interface SearchHistoryDao {

    @Query("select * from SearchHistory")
    suspend fun getAllSearchHistory(): List<SearchHistory>

    @Insert
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    @Delete
    suspend fun delete(searchHistory: SearchHistory)
}