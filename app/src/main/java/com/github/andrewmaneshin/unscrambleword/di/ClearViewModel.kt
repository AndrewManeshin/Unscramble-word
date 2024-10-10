package com.github.andrewmaneshin.unscrambleword.di

import com.github.andrewmaneshin.unscrambleword.core.MyViewModel

interface ClearViewModel {

    fun clear(clasz: Class<out MyViewModel>)
}