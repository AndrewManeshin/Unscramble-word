package com.github.andrewmaneshin.unscrambleword.load

import android.view.View
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class ErrorMessageUi(
    private val id: Int,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) {

    private val interaction = onView(
        allOf(
            withId(id),
            containerIdMatcher,
            containerClassTypeMatcher,
            isAssignableFrom(ProgressBar::class.java)
        )
    )

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun waitTillVisible() {
        onView(isRoot()).perform(waitTillDisplayed(id, 4000))
    }

    fun waitTillDoesNotExist() {
        onView(isRoot()).perform(waitTillDoesNotExist(id, 4000))
    }
}