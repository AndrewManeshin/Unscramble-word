package com.github.andrewmaneshin.unscrambleword.game

import com.github.andrewmaneshin.unscrambleword.IntCache
import com.github.andrewmaneshin.unscrambleword.ShuffleStrategy

interface GameRepository {

    fun scrambledWord(): String
    fun originalWord(): String
    fun next()
    fun isLastWord(): Boolean

    class Base(
        private val index: IntCache,
        private val shuffleStrategy: ShuffleStrategy
    ) : GameRepository {

        private val originalList = listOf("android", "develop")
        private val shuffledList = originalList.map { shuffleStrategy.shuffle(it) }

        override fun scrambledWord(): String = shuffledList[index.read()]

        override fun originalWord(): String = originalList[index.read()]

        override fun next() {
            index.save(if (index.read() == originalList.size) 0 else index.read() + 1)
        }

        override fun isLastWord(): Boolean = index.read() == originalList.size
    }
}