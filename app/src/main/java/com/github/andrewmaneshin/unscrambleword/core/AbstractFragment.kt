package com.github.andrewmaneshin.unscrambleword.core

import androidx.fragment.app.Fragment
import com.github.andrewmaneshin.unscrambleword.MyViewModel

abstract class AbstractFragment<UiState : Any, ViewModel : MyViewModel.Async<UiState>> :
    Fragment() {

    protected lateinit var viewModel: ViewModel

    protected abstract val update: (UiState) -> Unit

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }
}