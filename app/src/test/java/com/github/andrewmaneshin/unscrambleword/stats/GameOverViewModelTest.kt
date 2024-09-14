package com.github.andrewmaneshin.unscrambleword.stats

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
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

private class FakeClearViewModel : ClearViewModel {
    override fun clear(clasz: Class<out MyViewModel>) = Unit
}