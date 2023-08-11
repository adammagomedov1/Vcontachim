package com.example.vcontachim.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vcontachim.models.SearchHistory

@Database(
    entities = [SearchHistory::class],
    version = 1,
    exportSchema = true,
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

}