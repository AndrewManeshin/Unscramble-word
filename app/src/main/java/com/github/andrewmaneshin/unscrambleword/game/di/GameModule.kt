package com.github.andrewmaneshin.unscrambleword.game.di

import com.github.andrewmaneshin.unscrambleword.IntCache
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.game.data.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.data.ShuffleStrategy
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameUiObservable
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameViewModel

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel {
        val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)

        return GameViewModel(
            GameUiObservable.Base(),
            core.clearViewModel,
            if (core.runUiTest)
                GameRepository.Fake(
                    corrects,
                    incorrects,
                    IntCache.Base(core.sharedPreferences, "indexKey", 0),
                    ShuffleStrategy.Reverse()
                )
            else
                GameRepository.Base(
                    corrects,
                    incorrects,
                    IntCache.Base(core.sharedPreferences, "indexKey", 0),
                    ShuffleStrategy.Base(),
                    core.cacheModule.dao(),
                    core.cacheModule.clearDatabase(),
                    core.size
                ),
            core.runAsync
        )
    }
}