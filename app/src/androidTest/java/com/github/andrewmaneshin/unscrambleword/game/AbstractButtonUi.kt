package com.github.andrewmaneshin.unscrambleword.game

import androidx.test.espresso.ViewInteraction

abstract class AbstractButtonUi(
    protected val interaction: ViewInteraction
) {

    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }
}