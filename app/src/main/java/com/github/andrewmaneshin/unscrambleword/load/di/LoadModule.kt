package com.github.andrewmaneshin.unscrambleword.load.di

import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository
import com.github.andrewmaneshin.unscrambleword.load.data.ParseWords
import com.github.andrewmaneshin.unscrambleword.load.data.StringCache
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadViewModel
import com.github.andrewmaneshin.unscrambleword.load.presentation.UiObservable

class LoadModule(private val core: Core) : Module<LoadViewModel> {

    override fun viewModel() = LoadViewModel(
        LoadRepository.Base(
            ParseWords.Base(core.gson),
            StringCache.Base(core.sharedPreferences, "response_data", "")
        ),
        UiObservable.Base()
    )
}