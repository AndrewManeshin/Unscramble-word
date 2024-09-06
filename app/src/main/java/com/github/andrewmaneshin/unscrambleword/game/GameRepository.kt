package com.github.andrewmaneshin.unscrambleword.game

import com.github.andrewmaneshin.unscrambleword.IntCache

interface GameRepository {

    fun scrambledWord(): String
    fun originalWord(): String
    fun next()
    fun check(text: String): Boolean
    fun isLastWord(): Boolean
    fun clear()

    class Base(
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val index: IntCache,
        private val shuffleStrategy: ShuffleStrategy
    ) : GameRepository {

        private val originalList = listOf("android", "develop")
        private val shuffledList = originalList.map { shuffleStrategy.shuffle(it) }

        override fun scrambledWord(): String = shuffledList[index.read()]

        override fun originalWord(): String = originalList[index.read()]

        override fun next() {
            index.save(index.read() + 1)
        }

        override fun check(text: String) = if (originalWord().equals(text, true)) {
            corrects.save(corrects.read() + 1)
            true
        } else {
            incorrects.save(incorrects.read() + 1)
            false
        }

        override fun isLastWord(): Boolean = index.read() == originalList.size

        override fun clear() {
            index.save(0)
        }
    }
}