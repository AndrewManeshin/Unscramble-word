package com.github.andrewmaneshin.unscrambleword.game

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.andrewmaneshin.unscrambleword.R
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
            withId(R.id.scrambleWordTextView),
            withText(scrambledWord),
            isAssignableFrom(TextView::class.java)
        )
    )

    fun assertWordVisible() {
        interaction.check(matches(isCompletelyDisplayed()))
    }
}