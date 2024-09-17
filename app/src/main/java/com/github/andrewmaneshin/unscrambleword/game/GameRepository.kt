package com.github.andrewmaneshin.unscrambleword.game

import com.github.andrewmaneshin.unscrambleword.IntCache
import com.github.andrewmaneshin.unscrambleword.load.data.ParseWords
import com.github.andrewmaneshin.unscrambleword.load.data.StringCache

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
        private val shuffleStrategy: ShuffleStrategy,
        private val originalList: Array<String>
    ) : GameRepository {

        constructor(
            corrects: IntCache,
            incorrects: IntCache,
            index: IntCache,
            shuffleStrategy: ShuffleStrategy,
            dataCache: StringCache,
            parseWords: ParseWords
        ) : this(
            corrects, incorrects, index, shuffleStrategy, parseWords.parse(dataCache.read())
        )

        //        private val originalList = listOf("android", "develop")
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