package com.github.andrewmaneshin.unscrambleword.load.data.cloud

interface LoadResult {

    fun isSuccessful(): Boolean
    fun message(): String

    object Success : LoadResult {
        override fun isSuccessful() = true
        override fun message() = throw IllegalStateException("cannot happened")
    }

    data class Error(private val message: String) : LoadResult {
        override fun isSuccessful() = false
        override fun message() = message
    }
}