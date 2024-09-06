package com.github.andrewmaneshin.unscrambleword.di

import com.github.andrewmaneshin.unscrambleword.MyViewModel

interface ClearViewModel {

    fun clear(clasz: Class<out MyViewModel>)
}