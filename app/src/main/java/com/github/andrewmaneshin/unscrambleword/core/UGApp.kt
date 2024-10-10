package com.github.andrewmaneshin.unscrambleword.core

import android.app.Application
import android.os.StrictMode
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.ManageViewModels
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel

class UGApp : Application(), ProvideViewModel {

    private lateinit var factory: ManageViewModels

    override fun onCreate() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        )

        super.onCreate()
        val clearViewModel = object : ClearViewModel {
            override fun clear(clasz: Class<out MyViewModel>) {
                factory.clear(clasz)
            }
        }
        val make = ProvideViewModel.Make(Core(this, clearViewModel))
        factory = ManageViewModels.Factory(make)
    }

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
        return factory.makeViewModel(clasz)
    }
}