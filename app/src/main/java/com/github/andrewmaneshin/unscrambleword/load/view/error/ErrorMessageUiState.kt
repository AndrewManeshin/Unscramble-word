package com.github.andrewmaneshin.unscrambleword.load.view.error

import android.view.View
import androidx.annotation.StringRes
import java.io.Serializable

interface ErrorMessageUiState : Serializable {

    fun update(updateErrorMessage: UpdateErrorMessage)

    abstract class Abstract(private val visibility: Int) : ErrorMessageUiState {

        override fun update(updateErrorMessage: UpdateErrorMessage) {
            updateErrorMessage.updateVisibility(visibility)
        }
    }

    object Hide : Abstract(View.GONE)

    data class ShowRes(@StringRes private val resId: Int) : Abstract(View.VISIBLE) {

        override fun update(updateErrorMessage: UpdateErrorMessage) {
            super.update(updateErrorMessage)
            updateErrorMessage.updateMessage(resId)
        }
    }

    data class Show(private val message: String) : Abstract(View.VISIBLE) {

        override fun update(updateErrorMessage: UpdateErrorMessage) {
            super.update(updateErrorMessage)
            updateErrorMessage.updateMessage(message)
        }
    }
}