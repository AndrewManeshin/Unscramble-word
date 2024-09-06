package com.github.andrewmaneshin.unscrambleword.stats.di

import com.github.andrewmaneshin.unscrambleword.di.AbstractProvideViewModel
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel
import com.github.andrewmaneshin.unscrambleword.stats.GameOverViewModel

class ProvideGameOverViewModel(core: Core, nextChain: ProvideViewModel) :
    AbstractProvideViewModel(core, nextChain, GameOverViewModel::class.java) {

    override fun model(): Module<*> = GameOverModule(core)
}