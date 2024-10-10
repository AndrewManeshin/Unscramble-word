package com.github.andrewmaneshin.unscrambleword.game.data

import kotlin.random.Random

interface ShuffleStrategy {

    fun shuffle(source: String): String

    object Base : ShuffleStrategy {

        override fun shuffle(source: String): String {
            return source.toCharArray().apply {
                this.shuffle(Random(System.currentTimeMillis()))
            }.joinToString("")
        }
    }
}