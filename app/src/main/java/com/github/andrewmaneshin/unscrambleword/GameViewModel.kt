package com.github.andrewmaneshin.unscrambleword

class GameViewModel(
    private val repository: GameRepository
) {
    fun next(): GameUiState {
        repository.next()
        return init()
    }

    fun skip(): GameUiState {
        repository.next()
        return init()
    }

    fun check(text: String): GameUiState {
        val scrambledWord = repository.scrambledWord()
        return if (repository.originalWord().equals(text, true))
            GameUiState.Correct(scrambledWord)
        else
            GameUiState.Incorrect(scrambledWord)
    }

    fun handleUserInput(text: String): GameUiState {
        val scrambledWord = repository.scrambledWord()
        return if (text.length == scrambledWord.length)
            GameUiState.Sufficient(scrambledWord)
        else
            GameUiState.Insufficient(scrambledWord)
    }

    fun init(): GameUiState = GameUiState.Initial(repository.scrambledWord())
}
