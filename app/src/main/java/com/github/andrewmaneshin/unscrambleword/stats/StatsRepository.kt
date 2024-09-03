package com.github.andrewmaneshin.unscrambleword.stats

interface StatsRepository {
    fun stats(): Pair<Int, Int>
}