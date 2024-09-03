package com.github.andrewmaneshin.unscrambleword.game

import com.github.andrewmaneshin.unscrambleword.IntCache
import com.github.andrewmaneshin.unscrambleword.ShuffleStrategy

interface GameRepository {

    fun scrambledWord(): String
    fun originalWord(): String
    fun next()

    class Base(
        private val index: IntCache,
        private val shuffleStrategy: ShuffleStrategy
    ) : GameRepository {

        private val originalList = listOf("android", "develop")
        private val shuffledList = originalList.map { shuffleStrategy.shuffle(it) }

        override fun scrambledWord(): String = shuffledList[index.read()]

        override fun originalWord(): String = originalList[index.read()]

        override fun next() {
            if (index.read() == originalList.size - 1) index.save(0) else index.save(index.read() + 1)
        }
    }
}