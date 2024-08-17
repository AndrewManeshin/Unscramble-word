package com.github.andrewmaneshin.unscrambleword

interface GameRepository {

    fun scrambledWord(): String
    fun originalWord(): String
    fun next()
}
