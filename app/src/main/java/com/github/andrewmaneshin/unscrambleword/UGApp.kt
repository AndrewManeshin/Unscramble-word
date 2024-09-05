package com.github.andrewmaneshin.unscrambleword

import android.app.Application
import android.app.Application.MODE_PRIVATE
import android.content.Context
import com.github.andrewmaneshin.unscrambleword.ProvideViewModel.Make
import com.github.andrewmaneshin.unscrambleword.game.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.GameViewModel
import com.github.andrewmaneshin.unscrambleword.stats.GameOverViewModel
import com.github.andrewmaneshin.unscrambleword.stats.StatsRepository

class UGApp : Application(), ProvideViewModel {

    private lateinit var factory: ManageViewModels

    override fun onCreate() {
        super.onCreate()
        val make = ProvideViewModel.Make(
            object : ClearViewModel {
                override fun clear(clasz: Class<out MyViewModel>) {
                    factory.clear(clasz)
                }
            },
            Core(this)
        )

        factory = ManageViewModels.Factory(make)
    }

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
        return factory.makeViewModel(clasz)
    }
}

interface ManageViewModels : ProvideViewModel, ClearViewModel {
    class Factory(
        private val make: Make
    ) : ManageViewModels {
        private val viewModelMap = mutableMapOf<Class<out MyViewModel>, MyViewModel?>()

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
            if (viewModelMap[clasz] == null) {
                val viewModel = make.makeViewModel(clasz)
                viewModelMap.put(clasz, viewModel)
                viewModel
            } else
                viewModelMap[clasz] as T

        override fun clear(clasz: Class<out MyViewModel>) {
            viewModelMap[clasz] = null
        }
    }
}

interface ClearViewModel {
    fun clear(clasz: Class<out MyViewModel>)
}

class Core(context: Context) {

    val sharedPreferences = context.getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)
}

interface MyViewModel

interface ProvideViewModel {
    fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T


    class Make(
        private val clearViewModel: ClearViewModel,
        private val core: Core
    ) : ProvideViewModel {

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
            return when (clasz) {

                GameViewModel::class.java -> {
                    val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
                    val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)

                    GameViewModel(
                        clearViewModel,
                        GameRepository.Base(
                            corrects,
                            incorrects,
                            IntCache.Base(core.sharedPreferences, "indexKey", 0),
                            ShuffleStrategy.Reverse()
                        )
                    )
                }

                GameOverViewModel::class.java -> {
                    val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
                    val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)

                    GameOverViewModel(
                        clearViewModel,
                        StatsRepository.Base(
                            corrects,
                            incorrects
                        )
                    )
                }

                else -> throw IllegalStateException("unknown class $clasz")
            } as T
        }
    }
}