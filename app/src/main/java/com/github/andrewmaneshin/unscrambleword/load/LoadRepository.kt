package com.github.andrewmaneshin.unscrambleword.load

interface LoadRepository {

    fun load(resultCallback: (LoadResult) -> Unit)
}