package com.github.andrewmaneshin.unscrambleword.load.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordCache::class], version = 1)
abstract class WordsDatabase : RoomDatabase(), ClearDatabase {

    abstract fun dao(): WordsDao

    override suspend fun clear() = clearAllTables()
}

interface ClearDatabase {

    suspend fun clear()
}