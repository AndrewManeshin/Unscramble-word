package com.github.andrewmaneshin.unscrambleword.main

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module

class MainModule(core: Core) : Module<MainViewModel> {

    private val size = core.size
    private val indexCache = IntCache.Base(core.sharedPreferences, "index", size)

    override fun viewModel() = MainViewModel(indexCache, size)
}