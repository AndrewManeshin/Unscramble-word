package com.github.andrewmaneshin.unscrambleword.load

import com.google.gson.Gson

interface ParseWords {

    fun parse(source: String): Response

    class Base(
        private val gson: Gson
    ) : ParseWords {
        override fun parse(source: String): Response {
            return gson.fromJson(source, Response::class.java)
        }
    }
}