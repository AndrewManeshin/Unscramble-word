package com.github.andrewmaneshin.unscrambleword.game

import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ButtonUi(
    @StringRes text: Int,
    id: Int,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) : AbstractButtonUi(
    onView(
        allOf(
            withText(text),
            withId(id),
            containerIdMatcher,
            containerClassTypeMatcher,
            isAssignableFrom(Button::class.java)
        )
    )
) {

    fun assertNotEnabled() {
        interaction.check(matches(isNotEnabled()))
    }

    fun assertEnabled() {
        interaction.check(matches(isEnabled()))
    }

    fun assertNotVisible() {
        interaction.check(doesNotExist())
    }

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }
}