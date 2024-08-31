package com.github.andrewmaneshin.unscrambleword.game

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.github.andrewmaneshin.unscrambleword.R
import org.hamcrest.Matcher

class GamePage(scrambledWord: String) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))
    private val scrambleWordUi = ScrambleWordUi(
        scrambledWord = scrambledWord,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )
    private val inputUi = InputUI()
    private val skipUi = ButtonUi(
        text = R.string.skip,
        id = R.id.skipButton,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )
    private val checkUi = ButtonUi(
        text = R.string.check,
        id = R.id.checkButton,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )
    private val nextUi = ButtonUi(
        text = R.string.next,
        id = R.id.nextButton,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = classTypeMatcher
    )


    fun clickSkip() {
        skipUi.click()
    }

    fun assertInitialState() {
        scrambleWordUi.assertWordVisible()
        inputUi.assertInitialState()
        checkUi.assertNotEnabled()
        skipUi.assertEnabled()
        nextUi.assertNotVisible()
    }

    fun addInput(text: String) {
        inputUi.addInput(text = text)
    }

    fun assertInsufficientInputState() {
        scrambleWordUi.assertWordVisible()
        inputUi.assertInsufficientInputState()
        checkUi.assertNotEnabled()
        skipUi.assertEnabled()
        nextUi.assertNotVisible()
    }

    fun assertSufficientInputState() {
        scrambleWordUi.assertWordVisible()
        inputUi.assertSufficientInputState()
        checkUi.assertEnabled()
        skipUi.assertEnabled()
        nextUi.assertNotVisible()
    }

    fun clickCheck() {
        checkUi.click()
    }

    fun assertIncorrectState() {
        scrambleWordUi.assertWordVisible()
        checkUi.assertNotEnabled()
        skipUi.assertEnabled()
        nextUi.assertNotVisible()
        inputUi.assertIncorrectState()
    }

    fun assertCorrectState() {
        scrambleWordUi.assertWordVisible()
        checkUi.assertNotVisible()
        skipUi.assertNotVisible()
        nextUi.assertVisible()
        inputUi.assertCorrectState()
    }

    fun clickNext() {
        nextUi.click()
    }

    fun removeInputLastLetter() {
        inputUi.removeInputLastLetter()
    }

    fun assertNotVisible() {
        scrambleWordUi.assertDoesNotExist()
        checkUi.assertDoesNotExist()
        skipUi.assertDoesNotExist()
        nextUi.assertDoesNotExist()
        inputUi.assertDoesNotExist()
    }
}