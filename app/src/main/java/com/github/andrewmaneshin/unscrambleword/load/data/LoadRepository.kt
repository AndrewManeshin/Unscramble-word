package com.github.andrewmaneshin.unscrambleword.load.data

import java.net.HttpURLConnection
import java.net.URL

interface LoadRepository {

    fun load(): LoadResult

    class Base(
        private val parseWords: ParseWords,
        private val dataCache: StringCache
    ) : LoadRepository {

        private val url =
            "https://random-word-api.vercel.app/api?words=5&letter=a&type=uppercase&alphabetize=true"

        override fun load(): LoadResult {
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                val array = parseWords.parse(data)
                if (array.isEmpty()) {
                    return LoadResult.Error("Empty data, try again later")
                } else {
                    dataCache.save(data)
                    return LoadResult.Success
                }
            } catch (e: Exception) {
                return LoadResult.Error(e.message ?: "error")
            } finally {
                connection.disconnect()
            }
        }
    }
}

