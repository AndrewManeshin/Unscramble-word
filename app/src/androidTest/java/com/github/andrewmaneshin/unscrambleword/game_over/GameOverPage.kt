package com.github.andrewmaneshin.unscrambleword.game_over

import android.view.View
import android.widget.FrameLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.game.ButtonUi
import org.hamcrest.Matcher

class GameOverPage(corrects: Int, incorrects: Int) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.gameOverContainer))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(FrameLayout::class.java))

    private val statsUi = StatsUi(
        correct = corrects,
        incorrect = incorrects,
        containerIdMatcher,
        classTypeMatcher
    )

    private val newGameUi = ButtonUi(
        R.string.new_game,
        R.id.newGameButton,
        containerIdMatcher,
        classTypeMatcher
    )

    fun assertInitialState() {
        statsUi.assertVisible()
    }

    fun clickNewGame() {
        newGameUi.click()
    }

    fun assertNotVisible() {
        statsUi.assertDoesNotExist()
        newGameUi.assertDoesNotExist()
    }
}
