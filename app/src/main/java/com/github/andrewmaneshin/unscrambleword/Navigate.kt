package com.github.andrewmaneshin.unscrambleword

import com.github.andrewmaneshin.unscrambleword.game.presentation.GameScreen
import com.github.andrewmaneshin.unscrambleword.game.presentation.NavigateToGame
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadScreen
import com.github.andrewmaneshin.unscrambleword.load.presentation.NavigateToLoad
import com.github.andrewmaneshin.unscrambleword.stats.presentation.GameOverScreen
import com.github.andrewmaneshin.unscrambleword.stats.presentation.NavigateToGameOver

interface Navigate : NavigateToGame, NavigateToGameOver, NavigateToLoad {
    fun navigate(screen: Screen)

    override fun navigateToGame() {
        navigate(GameScreen)
    }

    override fun navigateToGameOver() {
        navigate(GameOverScreen)
    }

    override fun navigateToLoad() {
        navigate(LoadScreen)
    }
}