package com.github.andrewmaneshin.unscrambleword.game

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.view.scrambleword.ScrambleWordTextView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ScrambleWordUi(
    scrambledWord: String,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>,
) {

    private val interaction: ViewInteraction = onView(
        allOf(
            containerIdMatcher,
            containerClassTypeMatcher,
            withId(R.id.scrambledWordTextView),
            withText(scrambledWord),
            isAssignableFrom(ScrambleWordTextView::class.java)
        )
    )

    fun assertWordVisible() {
        interaction.check(matches(isDisplayed()))
    }
}