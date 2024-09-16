package com.github.andrewmaneshin.unscrambleword.load

import java.net.HttpURLConnection
import java.net.URL

interface LoadRepository {

    fun load(resultCallback: (LoadResult) -> Unit)

    class Base(
        private val parseWords: ParseWords,
        private val dataCache: StringCache
    ) : LoadRepository {

        private val url =
            "https://random-word-api.vercel.app/api?words=5&letter=a&type=uppercase&alphabetize=true"

        override fun load(resultCallback: (LoadResult) -> Unit) {
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                val response = parseWords.parse(data)
                val list = response.words
                if (list.isEmpty()) {
                    resultCallback.invoke(LoadResult.Error("Empty data, try again later"))
                } else {
                    dataCache.save(data)
                    resultCallback.invoke(LoadResult.Success)
                }
            } catch (e: Exception) {
                resultCallback.invoke(LoadResult.Error(e.message ?: "error"))
            } finally {
                connection.disconnect()
            }
        }
    }
}

