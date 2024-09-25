package com.github.andrewmaneshin.unscrambleword.game.data

import kotlin.random.Random

interface ShuffleStrategy {

    fun shuffle(source: String): String

    class Base : ShuffleStrategy {

        override fun shuffle(source: String): String {
            return source.toCharArray().apply {
                this.shuffle(Random(System.currentTimeMillis()))
            }.joinToString("")
        }
    }

    class Reverse : ShuffleStrategy {

        override fun shuffle(source: String): String {
            return source.reversed()
        }
    }
}