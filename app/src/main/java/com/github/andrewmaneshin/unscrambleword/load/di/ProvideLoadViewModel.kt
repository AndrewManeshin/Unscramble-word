package com.github.andrewmaneshin.unscrambleword.load.di

import com.github.andrewmaneshin.unscrambleword.di.AbstractProvideViewModel
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadViewModel

class ProvideLoadViewModel(core: Core, nextChain: ProvideViewModel) :
    AbstractProvideViewModel(core, nextChain, LoadViewModel::class.java) {

    override fun model(): Module<*> = LoadModule(core)
}