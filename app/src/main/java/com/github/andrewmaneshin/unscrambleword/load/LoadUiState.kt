package com.github.andrewmaneshin.unscrambleword.load

import com.github.andrewmaneshin.unscrambleword.load.view.error.UpdateErrorMessage
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.UpdateVisibility

interface LoadUiState {

    fun show(progress: UpdateVisibility, retryButton: UpdateVisibility, error: UpdateErrorMessage)

    object Success : LoadUiState {
        override fun show(
            progress: UpdateVisibility,
            retryButton: UpdateVisibility,
            error: UpdateErrorMessage
        ) {
            TODO("Not yet implemented")
        }
    }

    data class Error(private val message: String) : LoadUiState {
        override fun show(
            progress: UpdateVisibility,
            retryButton: UpdateVisibility,
            error: UpdateErrorMessage
        ) {
            TODO("Not yet implemented")
        }
    }

    object Progress : LoadUiState {
        override fun show(
            progress: UpdateVisibility,
            retryButton: UpdateVisibility,
            error: UpdateErrorMessage
        ) {
            TODO("Not yet implemented")
        }
    }
}