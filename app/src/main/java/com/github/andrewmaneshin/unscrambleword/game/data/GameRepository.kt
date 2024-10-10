package com.github.andrewmaneshin.unscrambleword.game.data

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.load.data.cache.ClearDatabase
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordsDao

interface GameRepository {

    suspend fun scrambledWord(): String
    suspend fun originalWord(): String
    fun next()
    suspend fun check(text: String): Boolean
    fun isLastWord(): Boolean
    suspend fun clear()

    class Base(
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val index: IntCache,
        private val dao: WordsDao,
        private val clearDatabase: ClearDatabase,
        private val size: Int
    ) : GameRepository {

        override suspend fun scrambledWord(): String = dao.fetchWord(index.read()).shuffledWord

        override suspend fun originalWord(): String = dao.fetchWord(index.read()).word

        override fun next() =
            index.save(index.read() + 1)

        override suspend fun check(text: String): Boolean =
            if (originalWord().equals(text, true)) {
                corrects.save(corrects.read() + 1)
                true
            } else {
                incorrects.save(incorrects.read() + 1)
                false
            }

        override fun isLastWord(): Boolean = index.read() == size

        override suspend fun clear() {
            clearDatabase.clear()
        }
    }

    class Fake(
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val index: IntCache,
        private val originalList: Array<String> = arrayOf("android", "develop")
    ) : GameRepository {

        private val shuffledList = originalList.map { it.reversed() }

        override suspend fun scrambledWord(): String = shuffledList[index.read()]

        override suspend fun originalWord(): String = originalList[index.read()]

        override fun next() {
            index.save(index.read() + 1)
        }

        override suspend fun check(text: String) = if (originalWord().equals(text, true)) {
            corrects.save(corrects.read() + 1)
            true
        } else {
            incorrects.save(incorrects.read() + 1)
            false
        }

        override fun isLastWord(): Boolean = index.read() == originalList.size

        override suspend fun clear() {
            index.save(0)
        }
    }
}