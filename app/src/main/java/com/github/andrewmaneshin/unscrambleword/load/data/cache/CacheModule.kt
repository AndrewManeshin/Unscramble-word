package com.github.andrewmaneshin.unscrambleword.load.data.cache

import android.content.Context
import androidx.room.Room
import com.github.andrewmaneshin.unscrambleword.R

interface CacheModule {

    fun dao(): WordsDao

    fun clearDatabase(): ClearDatabase

    class Base(applicationContext: Context) : CacheModule {

        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                WordsDatabase::class.java,
                applicationContext.getString(R.string.app_name)
            ).build()
        }

        override fun dao(): WordsDao = database.dao()

        override fun clearDatabase(): ClearDatabase = database
    }
}