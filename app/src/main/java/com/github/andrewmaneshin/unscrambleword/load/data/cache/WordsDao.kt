package com.github.andrewmaneshin.unscrambleword.load.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // TODO fix loading data every time the application is starting
    suspend fun saveWords(words: List<WordCache>)

    @Query("SELECT * FROM Words where id=:id")
    suspend fun fetchWord(id: Int): WordCache
}