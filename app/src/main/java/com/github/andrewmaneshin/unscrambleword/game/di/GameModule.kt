package com.github.andrewmaneshin.unscrambleword.game.di

import com.github.andrewmaneshin.unscrambleword.IntCache
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.game.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.GameViewModel
import com.github.andrewmaneshin.unscrambleword.game.ShuffleStrategy
import com.github.andrewmaneshin.unscrambleword.load.data.ParseWords
import com.github.andrewmaneshin.unscrambleword.load.data.StringCache

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel {
        val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)

        return GameViewModel(
            core.clearViewModel,
            GameRepository.Base(
                corrects,
                incorrects,
                IntCache.Base(core.sharedPreferences, "indexKey", 0),
                ShuffleStrategy.Reverse(),
                StringCache.Base(core.sharedPreferences, "response_data", ""),
                ParseWords.Base(core.gson)
            )
        )
    }
}