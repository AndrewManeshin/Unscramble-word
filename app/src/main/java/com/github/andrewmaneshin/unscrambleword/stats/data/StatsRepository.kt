package com.github.andrewmaneshin.unscrambleword.stats.data

import com.github.andrewmaneshin.unscrambleword.core.IntCache

interface StatsRepository {

    fun stats(): Pair<Int, Int>
    fun clear()

    class Base(
        private val corrects: IntCache,
        private val incorrects: IntCache
    ) : StatsRepository {

        override fun stats() = Pair(corrects.read(), incorrects.read())

        override fun clear() {
            corrects.save(0)
            incorrects.save(0)
        }
    }
}