package com.github.andrewmaneshin.unscrambleword.load.presentation

import androidx.annotation.StringRes
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.game.presentation.NavigateToGame
import com.github.andrewmaneshin.unscrambleword.load.view.error.ErrorMessageUiState
import com.github.andrewmaneshin.unscrambleword.load.view.error.UpdateErrorMessage
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.UpdateVisibility
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.VisibilityUiState

interface LoadUiState {

    fun show(progress: UpdateVisibility, retryButton: UpdateVisibility, error: UpdateErrorMessage)

    fun navigate(navigateToGame: NavigateToGame) = Unit

    abstract class Abstract(
        private val progressUiState: VisibilityUiState,
        private val retryUiState: VisibilityUiState,
        private val errorUiState: ErrorMessageUiState
    ) : LoadUiState {
        override fun show(
            progress: UpdateVisibility,
            retryButton: UpdateVisibility,
            error: UpdateErrorMessage
        ) {
            progress.update(progressUiState)
            retryButton.update(retryUiState)
            error.update(errorUiState)
        }
    }

    object Success :
        Abstract(VisibilityUiState.Gone, VisibilityUiState.Gone, ErrorMessageUiState.Hide) {
        override fun navigate(navigateToGame: NavigateToGame) {
            navigateToGame.navigateToGame()
        }
    }

    data class ErrorRes(@StringRes private val messageId: Int = R.string.no_internet_connection) :
        Abstract(
            VisibilityUiState.Gone,
            VisibilityUiState.Visible,
            ErrorMessageUiState.ShowRes(messageId)
        )

    data class Error(private val message: String) :
        Abstract(
            VisibilityUiState.Gone,
            VisibilityUiState.Visible,
            ErrorMessageUiState.Show(message)
        ) {

    }

    object Progress :
        Abstract(VisibilityUiState.Visible, VisibilityUiState.Gone, ErrorMessageUiState.Hide)
}