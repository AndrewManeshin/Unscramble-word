package com.github.andrewmaneshin.unscrambleword.game.di

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.game.data.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.data.ShuffleStrategy
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameUiObservable
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameViewModel

class GameModule(private val core: Core) : Module<GameViewModel> {

    private val indexCache: IntCache by lazy {
        IntCache.Base(core.sharedPreferences, "index", 0)
    }

    override fun viewModel() = GameViewModel(
        GameUiObservable.Base(),
        core.clearViewModel,
        if (core.runUiTest)
            GameRepository.Fake(
                core.correctsCache,
                core.incorrectsCache,
                indexCache,
                ShuffleStrategy.Reverse
            )
        else
            GameRepository.Base(
                core.correctsCache,
                core.incorrectsCache,
                indexCache,
                ShuffleStrategy.Base,
                core.cacheModule.dao(),
                core.cacheModule.clearDatabase(),
                core.size
            ),
        core.runAsync
    )
}