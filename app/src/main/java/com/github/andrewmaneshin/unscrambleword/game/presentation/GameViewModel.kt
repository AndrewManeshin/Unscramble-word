package com.github.andrewmaneshin.unscrambleword.game.presentation

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.RunAsync
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import com.github.andrewmaneshin.unscrambleword.game.data.GameRepository

class GameViewModel(
    uiObservable: GameUiObservable,
    private val clearViewModel: ClearViewModel,
    private val repository: GameRepository,
    private val runAsync: RunAsync
) : MyViewModel.Abstract<GameUiState>(uiObservable) {

    private val uiUpdate: (GameUiState) -> Unit = {
        uiObservable.postUiState(it)
    }

    fun next() {
        repository.next()
        return if (repository.isLastWord()) {
            runAsync.handleAsync(viewModelScope, {
                repository.clear()
                clearViewModel.clear(GameViewModel::class.java)
                GameUiState.Finish
            }, uiUpdate)
        } else
            init()
    }

    fun skip() = next()

    fun check(text: String) {
        runAsync.handleAsync(viewModelScope, {
            if (repository.check(text))
                GameUiState.Correct
            else
                GameUiState.Incorrect
        }, uiUpdate)
    }

    fun handleUserInput(text: String) {
        runAsync.handleAsync(viewModelScope, {
            val scrambledWord = repository.scrambledWord()
            if (text.length == scrambledWord.length)
                GameUiState.Sufficient
            else
                GameUiState.Insufficient
        }, uiUpdate)
    }

    fun init(isFirstRun: Boolean = true) {
        runAsync.handleAsync(viewModelScope, {
            if (isFirstRun) {
                GameUiState.Initial(repository.scrambledWord())
            } else {
                GameUiState.Empty
            }
        }, uiUpdate)
    }
}