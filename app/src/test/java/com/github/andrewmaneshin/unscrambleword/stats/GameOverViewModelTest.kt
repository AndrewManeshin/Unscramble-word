package com.github.andrewmaneshin.unscrambleword.stats

import com.github.andrewmaneshin.unscrambleword.FakeClearViewModel
import com.github.andrewmaneshin.unscrambleword.stats.data.StatsRepository
import com.github.andrewmaneshin.unscrambleword.stats.presentation.GameOverViewModel
import com.github.andrewmaneshin.unscrambleword.stats.view.stats.StatsUiState
import org.junit.Assert.assertEquals
import org.junit.Test

class GameOverViewModelTest {

    @Test
    fun test() {
        val repository = FakeRepository()
        val viewModel =
            GameOverViewModel(repository = repository, clearViewModel = FakeClearViewModel())

        assertEquals(StatsUiState.Base(2, 3), viewModel.init(true))
    }
}

private class FakeRepository : StatsRepository {

    override fun stats(): Pair<Int, Int> = Pair(2, 3)
    override fun clear() = Unit
}