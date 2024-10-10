package com.github.andrewmaneshin.unscrambleword.load.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Words")
data class WordCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("word")
    val word: String,
    @ColumnInfo("shuffled_word")
    val shuffledWord: String
)