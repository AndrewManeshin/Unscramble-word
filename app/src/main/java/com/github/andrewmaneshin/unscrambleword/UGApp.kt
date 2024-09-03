package com.github.andrewmaneshin.unscrambleword

import android.app.Application
import com.github.andrewmaneshin.unscrambleword.game.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.GameViewModel
import com.github.andrewmaneshin.unscrambleword.stats.GameOverViewModel
import com.github.andrewmaneshin.unscrambleword.stats.StatsRepository

class UGApp : Application() {

    lateinit var gameViewModel: GameViewModel
    lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)
        val corrects = IntCache.Base(sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(sharedPreferences, "incorrects", 0)

        gameViewModel = GameViewModel(
            GameRepository.Base(
                corrects,
                incorrects,
                IntCache.Base(sharedPreferences, "indexKey", 0),
                ShuffleStrategy.Reverse()
            )
        )

        gameOverViewModel = GameOverViewModel(
            StatsRepository.Base(
                corrects,
                incorrects
            )
        )
    }
}

