package com.github.andrewmaneshin.unscrambleword

import android.app.Application
import com.github.andrewmaneshin.unscrambleword.game.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.GameViewModel
import com.github.andrewmaneshin.unscrambleword.stats.GameOverViewModel

class UGApp : Application() {

    lateinit var gameViewModel: GameViewModel
    lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate() {
        super.onCreate()

        gameViewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(
                    getSharedPreferences(
                        R.string.app_name.toString(),
                        MODE_PRIVATE
                    ), "indexKey", 0
                ),
                ShuffleStrategy.Reverse()
            )
        )

//        gameOverViewModel = GameOverViewModel()
    }
}

