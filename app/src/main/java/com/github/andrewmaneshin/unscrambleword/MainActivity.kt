package com.github.andrewmaneshin.unscrambleword

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.andrewmaneshin.unscrambleword.game.GameScreen
import com.github.andrewmaneshin.unscrambleword.game.NavigateToGame
import com.github.andrewmaneshin.unscrambleword.stats.GameOverScreen
import com.github.andrewmaneshin.unscrambleword.stats.NavigateToGameOver

class MainActivity : AppCompatActivity(), Navigate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToGame()
        }
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }
}

interface Navigate : NavigateToGame, NavigateToGameOver {
    fun navigate(screen: Screen)

    override fun navigateToGame() {
        navigate(GameScreen)
    }

    override fun navigateToGameOver() {
        navigate(GameOverScreen)
    }
}