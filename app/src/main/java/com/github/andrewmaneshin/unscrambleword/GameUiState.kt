package com.github.andrewmaneshin.unscrambleword

import com.github.andrewmaneshin.unscrambleword.view.check.CheckUiState
import com.github.andrewmaneshin.unscrambleword.view.check.UpdateCheckButton
import com.github.andrewmaneshin.unscrambleword.view.input.InputUiState
import com.github.andrewmaneshin.unscrambleword.view.input.UpdateInput
import com.github.andrewmaneshin.unscrambleword.view.scrambleword.UpdateText
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.UpdateVisibility
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.VisibilityUiState

interface GameUiState {

    fun update(
        scrambleTextView: UpdateText,
        inputView: UpdateInput,
        checkButton: UpdateCheckButton,
        skipButton: UpdateVisibility,
        nextButton: UpdateVisibility
    )

    object Empty : GameUiState {

        override fun update(
            scrambleTextView: UpdateText,
            inputView: UpdateInput,
            checkButton: UpdateCheckButton,
            skipButton: UpdateVisibility,
            nextButton: UpdateVisibility
        ) = Unit
    }

    abstract class Abstract(
        private val checkUiState: CheckUiState,
        private val inputUiState: InputUiState
    ) : GameUiState {
        override fun update(
            scrambleTextView: UpdateText,
            inputView: UpdateInput,
            checkButton: UpdateCheckButton,
            skipButton: UpdateVisibility,
            nextButton: UpdateVisibility
        ) {
            checkButton.update(checkUiState)
            inputView.update(inputUiState)
        }
    }

    data class Initial(
        private val scrambledWord: String
    ) : Abstract(
        CheckUiState.Disabled,
        InputUiState.Base
    ) {

        override fun update(
            scrambleTextView: UpdateText,
            inputView: UpdateInput,
            checkButton: UpdateCheckButton,
            skipButton: UpdateVisibility,
            nextButton: UpdateVisibility
        ) {
            super.update(scrambleTextView, inputView, checkButton, skipButton, nextButton)
            scrambleTextView.update(scrambledWord)
            inputView.update("")
            skipButton.update(VisibilityUiState.Visible)
            nextButton.update(VisibilityUiState.Gone)
        }
    }

    object Insufficient : Abstract(
        CheckUiState.Disabled,
        InputUiState.Base
    )

    object Sufficient : Abstract(
        CheckUiState.Enabled,
        InputUiState.Base
    )

    object Incorrect : Abstract(
        CheckUiState.Disabled,
        InputUiState.Incorrect
    )

    object Correct : Abstract(
        CheckUiState.Invisible,
        InputUiState.Correct
    ) {

        override fun update(
            scrambleTextView: UpdateText,
            inputView: UpdateInput,
            checkButton: UpdateCheckButton,
            skipButton: UpdateVisibility,
            nextButton: UpdateVisibility
        ) {
            super.update(scrambleTextView, inputView, checkButton, skipButton, nextButton)
            skipButton.update(VisibilityUiState.Gone)
            nextButton.update(VisibilityUiState.Visible)
        }
    }
}