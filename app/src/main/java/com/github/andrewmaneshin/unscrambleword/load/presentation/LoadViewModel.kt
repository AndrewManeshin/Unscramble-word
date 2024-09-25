package com.github.andrewmaneshin.unscrambleword.load.presentation

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.RunAsync
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val observable: UiObservable,
    private val runAsync: RunAsync
) : MyViewModel {

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            runAsync.handleAsync(
                {
                    val result = repository.load()
                    if (result.isSuccessful()) {
                        LoadUiState.Success
                    }
                    else
                        LoadUiState.Error(result.message())
                }) {
                observable.postUiState(it)
            }
        }
    }

    fun startUpdates(observer: (LoadUiState) -> Unit) = observable.register(observer)

    fun stopUpdates() = observable.unregister()
}