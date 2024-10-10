package com.github.andrewmaneshin.unscrambleword.main

import com.github.andrewmaneshin.unscrambleword.di.AbstractProvideViewModel
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel

class ProvideMainViewModel(core: Core, nextChain: ProvideViewModel) :
    AbstractProvideViewModel(core, nextChain, MainViewModel::class.java) {

    override fun model(): Module<*> = MainModule(core)
}
