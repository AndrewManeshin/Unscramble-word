package com.github.andrewmaneshin.unscrambleword

interface GameRepository {

    fun scrambledWord(): String
    fun originalWord(): String
    fun next()

    class Base(
        private val shuffleStrategy: ShuffleStrategy
    ) : GameRepository {

        private var index = 0

        private val originalList = listOf("android", "develop")

        override fun scrambledWord(): String = shuffleStrategy.shuffle(originalList[index])

        override fun originalWord(): String = originalList[index]

        override fun next() {
            if (index == originalList.size - 1) index = 0 else ++index
        }
    }
}