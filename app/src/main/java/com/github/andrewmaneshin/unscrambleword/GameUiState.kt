package com.github.andrewmaneshin.unscrambleword

import android.view.View
import com.github.andrewmaneshin.unscrambleword.databinding.ActivityMainBinding
import java.io.Serializable

interface GameUiState : Serializable {

    fun update(binding: ActivityMainBinding)

    abstract class Abstract(
        private val scrambledWord: String,
        private val inputUiState: InputUiState,
        private val checkUiState: CheckUiState,
        private val nextButtonVisibility: Int = View.GONE,
        private val skipButtonVisibility: Int = View.VISIBLE
    ) : GameUiState {
        override fun update(binding: ActivityMainBinding) {
            with(binding) {
                scrambledWordTextView.text = scrambledWord
                inputUiState.update(inputLayout, inputEditText)
                checkUiState.update(checkButton)
                nextButton.visibility = nextButtonVisibility
                skipButton.visibility = skipButtonVisibility
            }
        }
    }

    data class Initial(
        private val scrambledWord: String
    ) : Abstract(
        scrambledWord,
        inputUiState = InputUiState.Initial,
        checkUiState = CheckUiState.Disabled
    )

    data class Insufficient(
        private val scrambledWord: String
    ) : Abstract(
        scrambledWord,
        inputUiState = InputUiState.Insufficient,
        checkUiState = CheckUiState.Disabled
    )

    data class Sufficient(
        private val scrambledWord: String
    ) : Abstract(
        scrambledWord,
        inputUiState = InputUiState.Sufficient,
        checkUiState = CheckUiState.Enabled
    )

    data class Incorrect(
        private val scrambledWord: String
    ) : Abstract(
        scrambledWord,
        inputUiState = InputUiState.Incorrect,
        checkUiState = CheckUiState.Disabled
    )

    data class Correct(
        private val scrambledWord: String
    ) : Abstract(
        scrambledWord,
        inputUiState = InputUiState.Correct,
        checkUiState = CheckUiState.Invisible,
        skipButtonVisibility = View.GONE,
        nextButtonVisibility = View.VISIBLE
    )
}