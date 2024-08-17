package com.github.andrewmaneshin.unscrambleword

import androidx.viewbinding.ViewBinding

interface GameUiState {

    fun update(binding: ViewBinding)

    data class Initial(
        private val scrambledWord: String
    ) : GameUiState {
        override fun update(binding: ViewBinding) {
            TODO("Not yet implemented")
        }
    }

    data class Insufficient(
        private val scrambledWord: String
    ) : GameUiState {
        override fun update(binding: ViewBinding) {
            TODO("Not yet implemented")
        }
    }

    data class Sufficient(
        private val scrambledWord: String
    ) : GameUiState {
        override fun update(binding: ViewBinding) {
            TODO("Not yet implemented")
        }
    }

    data class Incorrect(
        private val scrambledWord: String
    ) : GameUiState {
        override fun update(binding: ViewBinding) {
            TODO("Not yet implemented")
        }
    }

    data class Correct(
        private val scrambledWord: String
    ) : GameUiState {
        override fun update(binding: ViewBinding) {
            TODO("Not yet implemented")
        }
    }
}
