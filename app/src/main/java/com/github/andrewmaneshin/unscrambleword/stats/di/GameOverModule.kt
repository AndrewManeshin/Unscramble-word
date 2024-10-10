package com.github.andrewmaneshin.unscrambleword.stats.di

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.stats.data.StatsRepository
import com.github.andrewmaneshin.unscrambleword.stats.presentation.GameOverViewModel

class GameOverModule(private val core: Core) : Module<GameOverViewModel> {

    override fun viewModel(): GameOverViewModel {
        val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)

        return GameOverViewModel(
            core.clearViewModel,
            StatsRepository.Base(
                corrects,
                incorrects
            )
        )
    }
}