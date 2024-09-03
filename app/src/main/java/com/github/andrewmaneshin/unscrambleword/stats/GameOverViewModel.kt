package com.github.andrewmaneshin.unscrambleword.stats

class GameOverViewModel(
    private val repository: StatsRepository
) {

    fun statsUiState(): StatsUiState {
        val (corrects, incorrects) = repository.stats()
        return StatsUiState.Base(corrects, incorrects)
    }
}