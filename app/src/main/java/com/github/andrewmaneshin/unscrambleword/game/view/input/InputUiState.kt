package com.github.andrewmaneshin.unscrambleword.game.view.input

import java.io.Serializable

interface InputUiState : Serializable {

    fun update(updateInput: UpdateInput)

    abstract class Abstract(
        private val errorIsVisible: Boolean,
        private val enabled: Boolean,
    ) : InputUiState {

        override fun update(updateInput: UpdateInput) {
            updateInput.update(errorIsVisible, enabled)
        }
    }

    object Base : Abstract(false, true)
    object Correct : Abstract(false, false)
    object Incorrect : Abstract(true, true)
}