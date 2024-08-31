package com.github.andrewmaneshin.unscrambleword.game

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist

abstract class AbstractButtonUi(
    protected val interaction: ViewInteraction
) {

    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }

    fun assertDoesNotExist() {
        interaction.check(doesNotExist())
    }
}