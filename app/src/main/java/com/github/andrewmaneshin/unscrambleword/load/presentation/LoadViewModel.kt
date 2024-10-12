package com.github.andrewmaneshin.unscrambleword.load.presentation

import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.core.MyViewModel
import com.github.andrewmaneshin.unscrambleword.core.RunAsync
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import com.github.andrewmaneshin.unscrambleword.load.data.BackendException
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository
import com.github.andrewmaneshin.unscrambleword.load.data.NoInternetConnectionException

class LoadViewModel(
    private val repository: LoadRepository,
    observable: LoadUiObservable,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel
) : MyViewModel.Abstract<LoadUiState>(observable) {

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            runAsync.handleAsync(viewModelScope, {
                try {
                    repository.load()
                    clearViewModel.clear(LoadViewModel::class.java)
                    LoadUiState.Success
                } catch (e: Exception) {
                    when (e) {
                        is NoInternetConnectionException -> LoadUiState.ErrorRes()
                        is BackendException -> LoadUiState.Error(e.message)
                        else -> LoadUiState.ErrorRes(R.string.service_is_unavailable)
                    }
                }
            }) {
                observable.postUiState(it)
            }
        }
    }
}