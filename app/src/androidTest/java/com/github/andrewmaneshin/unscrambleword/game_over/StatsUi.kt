package com.github.andrewmaneshin.unscrambleword.game_over

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.andrewmaneshin.unscrambleword.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class StatsUi(
    correct: Int,
    incorrect: Int,
    containerIdMatcher: Matcher<View>,
    classTypeMatcher: Matcher<View>
) {

    private val interaction = onView(
        allOf(
            withId(R.id.statsTextView),
            withText("Game Over\nCorrects: $correct\nIncorrects: $incorrect"),
            isAssignableFrom(TextView::class.java),
            containerIdMatcher,
            classTypeMatcher
        )
    )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertDoesNotExist() {
        interaction.check(doesNotExist())
    }
}
