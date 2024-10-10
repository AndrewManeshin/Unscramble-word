package com.github.andrewmaneshin.unscrambleword.load.presentation

import com.github.andrewmaneshin.unscrambleword.core.MyViewModel
import com.github.andrewmaneshin.unscrambleword.core.RunAsync
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val uiObservable: LoadUiObservable,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel,
    private val size: Int
) : MyViewModel.Abstract<LoadUiState>(uiObservable) {

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            uiObservable.postUiState(LoadUiState.Progress)
            runAsync.handleAsync(
                viewModelScope,
                {
                    val result = repository.load(size)
                    if (result.isSuccessful()) {
                        clearViewModel.clear(LoadViewModel::class.java)
                        LoadUiState.Success
                    } else
                        LoadUiState.Error(result.message())
                }) {
                uiObservable.postUiState(it)
            }
        }
    }
}