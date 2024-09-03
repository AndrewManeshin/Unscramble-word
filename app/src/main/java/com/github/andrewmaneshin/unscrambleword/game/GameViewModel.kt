package com.github.andrewmaneshin.unscrambleword.game

class GameViewModel(
    private val repository: GameRepository
) {

    fun next(): GameUiState {
        repository.next()
        return if (repository.isLastWord()) {
            repository.clear()
            GameUiState.Finish
        } else
            init()
    }

    fun skip() = next()

    fun check(text: String): GameUiState {
        return if (repository.check(text))
            GameUiState.Correct
        else
            GameUiState.Incorrect
    }

    fun handleUserInput(text: String): GameUiState {
        val scrambledWord = repository.scrambledWord()
        return if (text.length == scrambledWord.length)
            GameUiState.Sufficient
        else
            GameUiState.Insufficient
    }

    fun init(isFirstRun: Boolean = true): GameUiState = if (isFirstRun) {
        GameUiState.Initial(repository.scrambledWord())
    } else {
        GameUiState.Empty
    }
}
