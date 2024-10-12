package com.github.andrewmaneshin.unscrambleword.load.data

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.game.data.ShuffleStrategy
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordCache
import com.github.andrewmaneshin.unscrambleword.load.data.cache.WordsDao
import com.github.andrewmaneshin.unscrambleword.load.data.cloud.CloudDataSource
import kotlinx.coroutines.delay
import okio.IOException

interface LoadRepository {

    suspend fun load()

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: WordsDao,
        private val index: IntCache,
        private val shuffleStrategy: ShuffleStrategy
    ) : LoadRepository {

        override suspend fun load() {
            try {
                val data = cloudDataSource.load()
                cacheDataSource.saveWords(data.mapIndexed { index, word ->
                    WordCache(id = index, word = word, shuffleStrategy.shuffle(word))
                })
                index.save(0)
            } catch (e: Exception) {
                if (e is IOException)
                    throw NoInternetConnectionException()
                else if (e is IllegalArgumentException)
                    throw BackendException(e.message ?: "")
                throw ServiceUnavailableException()
            }
        }
    }

    class Fake : LoadRepository {

        private var count = 0

        override suspend fun load() {
            delay(2000)
            if (count++ == 0)
                throw NoInternetConnectionException()
            else
                LoadResult.Success
        }
    }
}

class NoInternetConnectionException : Exception()

class BackendException(override val message: String) : Exception()

class ServiceUnavailableException : Exception()