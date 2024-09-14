package com.github.andrewmaneshin.unscrambleword.stats

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import com.github.andrewmaneshin.unscrambleword.stats.view.stats.StatsUiState

class GameOverViewModel(
    private val clearViewModel: ClearViewModel,
    private val repository: StatsRepository
) : MyViewModel {

    fun init(isFirstRun: Boolean): StatsUiState {
        if (isFirstRun) {
            val (corrects, incorrects) = repository.stats()
            repository.clear()
            return StatsUiState.Base(corrects, incorrects)
        } else return StatsUiState.Empty
    }

    fun clear() {
        clearViewModel.clear(GameOverViewModel::class.java)
    }
}