package com.github.andrewmaneshin.unscrambleword.di

import com.github.andrewmaneshin.unscrambleword.MyViewModel

interface ManageViewModels : ProvideViewModel, ClearViewModel {

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val make: ProvideViewModel.Make
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