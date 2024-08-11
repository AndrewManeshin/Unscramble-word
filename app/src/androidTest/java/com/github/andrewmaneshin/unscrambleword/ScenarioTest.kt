package com.github.andrewmaneshin.unscrambleword

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.andrewmaneshin.unscrambleword.game.GamePage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var gamePage: GamePage

    @Before
    fun setUp() {
        gamePage = GamePage(scrambledWord = "android")
    }

    /**
     * UGTC-01 SkipTestCase
     */
    @Test
    fun skipTest() {
        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "chrome")
        gamePage.assertInitialState()
    }

    /**
     * UGTC-02 InsufficientInputTestCase
     */
    @Test
    fun InsufficientInputTest() {
        gamePage.addInput("adnroi")
        gamePage.assertInsufficientInputState()

        gamePage.addInput("d")
        gamePage.assertInsufficientInputState()
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        gamePage.addInput("androit")
        gamePage.assertSufficientInputState()
    }

    /**
     * UGTC-04 Sufficient and Insufficient InputTestCase
     */
    @Test
    fun SufficientAndInsufficientInputTest() {
        gamePage.addInput("adnroi")
        gamePage.assertInsufficientInputState()

        gamePage.addInput("dd")
        gamePage.assertInsufficientInputState()

        gamePage.removeInputLastLetter()
        gamePage.assertSufficientInputState()

        gamePage.removeInputLastLetter()
        gamePage.assertInsufficientInputState()
    }

    /**
     * UGTC-05 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        gamePage.addInput("androit")

        gamePage.clickCheck()
        gamePage.assertIncorrectState()
    }

    /**
     * UGTC-06 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        gamePage.addInput("androit")

        gamePage.clickCheck()
        gamePage.assertIncorrectState()

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "chrome")
        gamePage.assertInitialState()
    }

    /**
     * UGTC-07 CorrectAfterIncorrectTestCase
     */
    @Test
    fun CorrectAfterIncorrectTest() {
        gamePage.addInput("androit")

        gamePage.clickCheck()
        gamePage.assertIncorrectState()

        gamePage.addInput("androi")
        gamePage.assertInsufficientInputState()

        gamePage.addInput("android")
        gamePage.clickCheck()
        gamePage.assertCorrectState()

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "chrome")
        gamePage.assertInitialState()
    }
}