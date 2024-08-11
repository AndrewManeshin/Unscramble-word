package com.github.andrewmaneshin.unscrambleword.game

import android.view.KeyEvent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.andrewmaneshin.unscrambleword.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class InputUI(
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) {

    private val layoutInteraction: ViewInteraction = onView(
        allOf(
            withId(R.id.inputLayout),
            containerIdMatcher,
            containerClassTypeMatcher,
            isAssignableFrom(TextInputLayout::class.java)
        )
    )

    private val inputInteraction: ViewInteraction = onView(
        allOf(
            withId(R.id.inputEditText),
            withParent(R.id.inputLayout),
            withParent(isAssignableFrom(TextInputLayout::class.java)),
            isAssignableFrom(TextInputEditText::class.java)
        )
    )
    private val textInputLayoutErrorEnabledMatcherFalse = TextInputLayoutErrorEnabledMatcher(false)

    fun assertInsufficientInputState() {
        layoutInteraction.check(matches(isEnabled()))
        inputInteraction.check(matches(textInputLayoutErrorEnabledMatcherFalse))
    }

    fun assertSufficientInputState() {
        layoutInteraction.check(matches(isEnabled()))
        inputInteraction.check(matches(textInputLayoutErrorEnabledMatcherFalse))
    }

    fun assertInitialState() {
        layoutInteraction.check(matches(isEnabled()))
        inputInteraction.check(
            matches(
                allOf(
                    withText(""),
                    textInputLayoutErrorEnabledMatcherFalse
                )
            )
        )
    }

    fun assertIncorrectState() {
        layoutInteraction.check(matches(isEnabled()))
        inputInteraction.check(matches(TextInputLayoutErrorEnabledMatcher(true)))
    }

    fun assertCorrectState() {
        layoutInteraction.check(matches(isNotEnabled()))
        inputInteraction.check(
            matches(
                allOf(
                    withText(""),
                    textInputLayoutErrorEnabledMatcherFalse
                )
            )
        )
    }

    fun removeInputLastLetter() {
        inputInteraction.perform(click(), pressKey(KeyEvent.KEYCODE_DEL), closeSoftKeyboard())
    }

    fun addInput(text: String) {
        inputInteraction.perform(typeText(text), closeSoftKeyboard())
    }
}