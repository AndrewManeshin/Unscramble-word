package com.github.andrewmaneshin.unscrambleword.load.data

import com.google.gson.Gson

interface ParseWords {

    fun parse(source: String): Array<String>

    class Base(
        private val gson: Gson
    ) : ParseWords {
        override fun parse(source: String): Array<String> {
            return gson.fromJson(source, Array<String>::class.java)
        }
    }
}