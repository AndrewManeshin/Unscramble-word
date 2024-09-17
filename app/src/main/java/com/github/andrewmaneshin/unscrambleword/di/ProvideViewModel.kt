package com.github.andrewmaneshin.unscrambleword.di

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.game.di.ProvideGameViewModel
import com.github.andrewmaneshin.unscrambleword.load.di.ProvideLoadViewModel
import com.github.andrewmaneshin.unscrambleword.stats.di.ProvideGameOverViewModel

interface ProvideViewModel {
    fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T

    class Make(core: Core) : ProvideViewModel {

        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideLoadViewModel(core, chain)
            chain = ProvideGameViewModel(core, chain)
            chain = ProvideGameOverViewModel(core, chain)
        }

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
            chain.makeViewModel(clasz)
    }

    class Error : ProvideViewModel {
        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
            throw IllegalStateException("unknown class $clasz")
    }
}