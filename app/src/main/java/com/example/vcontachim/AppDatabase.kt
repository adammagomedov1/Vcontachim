package com.example.vcontachim

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vcontachim.database.SearchHistoryDao
import com.example.vcontachim.models.SearchHistory

@Database(
    entities = arrayOf(SearchHistory::class),
    version = 1,
    exportSchema = true,
//    autoMigrations = [AutoMigration(from = 0, to = 1)]
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun SearchHistoryDao(): SearchHistoryDao

}