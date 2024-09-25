package com.github.andrewmaneshin.unscrambleword.game.presentation

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import com.github.andrewmaneshin.unscrambleword.game.data.GameRepository

class GameViewModel(
    private val clearViewModel: ClearViewModel,
    private val repository: GameRepository
) : MyViewModel {

    fun next(): GameUiState {
        repository.next()
        return if (repository.isLastWord()) {
            repository.clear()
            clearViewModel.clear(GameViewModel::class.java)
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