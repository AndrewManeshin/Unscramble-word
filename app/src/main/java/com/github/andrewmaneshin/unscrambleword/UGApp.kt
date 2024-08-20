package com.github.andrewmaneshin.unscrambleword

import android.app.Application

class UGApp : Application() {

    lateinit var gameViewModel: GameViewModel

    override fun onCreate() {
        super.onCreate()
        gameViewModel = GameViewModel(GameRepository.Base(ShuffleStrategy.Reverse()))
    }
}