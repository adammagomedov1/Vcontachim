package com.example.vcontachim.database

import androidx.room.*
import com.example.vcontachim.models.SearchHistory

@Dao
interface SearchHistoryDao {

    @Query("select * from SearchHistory")
    suspend fun getAllSearchHistory(): List<SearchHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    @Delete
    suspend fun delete(searchHistory: SearchHistory)

    @Query("delete from SearchHistory")
    suspend fun deleteList()
}