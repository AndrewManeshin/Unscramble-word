package com.github.andrewmaneshin.unscrambleword.di

import com.github.andrewmaneshin.unscrambleword.core.MyViewModel

interface Module<T : MyViewModel> {

    fun viewModel(): T
}