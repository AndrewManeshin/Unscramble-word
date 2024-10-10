package com.github.andrewmaneshin.unscrambleword

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordCache
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordsDao
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordsDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: WordsDao
    private lateinit var database: WordsDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WordsDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = database.dao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testSaveAndFetch() = runBlocking {
        dao.saveWords(
            listOf(
                WordCache(1, "android", "diordna"),
                WordCache(2, "develop", "poleved")
            )
        )

        var expected = WordCache(1, "android", "diordna")
        var actual = dao.fetchWord(1)
        assertEquals(expected, actual)

        expected = WordCache(2, "develop", "poleved")
        actual = dao.fetchWord(2)
        assertEquals(expected, actual)
    }
}