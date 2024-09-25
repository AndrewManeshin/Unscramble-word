package com.github.andrewmaneshin.unscrambleword.game.di

import com.github.andrewmaneshin.unscrambleword.di.AbstractProvideViewModel
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameViewModel

class ProvideGameViewModel(core: Core, nextChain: ProvideViewModel) :
    AbstractProvideViewModel(core, nextChain, GameViewModel::class.java) {

    override fun model(): Module<*> = GameModule(core)
}