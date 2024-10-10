package com.github.andrewmaneshin.unscrambleword.di

import com.github.andrewmaneshin.unscrambleword.core.MyViewModel

@Suppress("UNCHECKED_CAST")
abstract class AbstractProvideViewModel(
    protected val core: Core,
    private val nextChain: ProvideViewModel,
    private val viewModelClass: Class<out MyViewModel>
) : ProvideViewModel {

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
        return if (clasz == viewModelClass) {
            model().viewModel() as T
        } else
            nextChain.makeViewModel(clasz)
    }

    protected abstract fun model(): Module<*>
}