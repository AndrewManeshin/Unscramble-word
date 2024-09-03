package com.github.andrewmaneshin.unscrambleword.stats

class GameOverViewModel(private val repository: StatsRepository) {

    fun init(isFirstRun: Boolean): StatsUiState {
        if (isFirstRun) {
            val (corrects, incorrects) = repository.stats()
            repository.clear()
            return StatsUiState.Base(corrects, incorrects)
        } else return StatsUiState.Empty
    }
}