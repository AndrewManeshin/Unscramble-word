package com.github.andrewmaneshin.unscrambleword

import android.app.Application
import android.content.Context

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
                        Context.MODE_PRIVATE
                    ), "indexKey", 0
                ),
                ShuffleStrategy.Reverse()
            )
        )

        gameOverViewModel = GameOverViewModel()
    }
}

