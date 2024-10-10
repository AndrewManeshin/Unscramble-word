package com.github.andrewmaneshin.unscrambleword.main

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.core.MyViewModel
import com.github.andrewmaneshin.unscrambleword.core.Screen
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameScreen
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadScreen

class MainViewModel(
    private val index: IntCache,
    private val size: Int
) : MyViewModel {

    fun firstScreen(isFirstRun: Boolean): Screen =
        if (isFirstRun) {
            if (index.read() == size)
                LoadScreen
            else
                GameScreen
        } else
            Screen.Empty
}