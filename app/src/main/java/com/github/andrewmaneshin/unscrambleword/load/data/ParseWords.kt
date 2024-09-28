package com.github.andrewmaneshin.unscrambleword.load.data

import com.google.gson.Gson

interface ParseWords {

    fun parse(source: String): WordsResponse

    fun toString(data: Any): String

    class Base(
        private val gson: Gson
    ) : ParseWords {

        override fun parse(source: String): WordsResponse {
            return gson.fromJson(source, WordsResponse::class.java)
        }

        override fun toString(data: Any): String {
            return gson.toJson(data)
        }
    }
}