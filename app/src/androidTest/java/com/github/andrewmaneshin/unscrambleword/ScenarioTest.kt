package com.github.andrewmaneshin.unscrambleword

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
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
        gamePage = GamePage(scrumbledWord = "android")
    }

    /**
     * UGTC-01 SkipTestCase
     */
    @Test
    fun skipTest() {
        gamePage.clickSkip()
        gamePage = GamePage(scrumbledWord = "chrome")
        gamePage().assertInitialState()
    }

    /**
     * UGTC-02 InsufficientInputTestCase
     */
    @Test
    fun InsufficientInputTest() {
        gamePage.input("adnroi")
        gamePage.assertInsufficientInputState()

        gamePage.input("androidd")
        gamePage.assertInsufficientInputState()
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        gamePage.input("androit")
        gamePage.assertSufficientInputState()
    }

    /**
     * UGTC-04 Sufficient and Insufficient InputTestCase
     */
    @Test
    fun SufficientAndInsufficientInputTest() {
        gamePage.input("adnroi")
        gamePage.assertInsufficientInputState()

        gamePage.input("androidd")
        gamePage.assertInsufficientInputState()

        gamePage.input("androit")
        gamePage.assertSufficientInputState()
    }

    /**
     * UGTC-05 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        gamePage.input("androit")

        gamePage.clickCheck()
        gamePage.assertIncorrectState()
    }

    /**
     * UGTC-06 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        gamePage.input("androit")

        gamePage.clickCheck()
        gamePage.assertIncorrectState()

        gamePage.clickSkip()
        gamePage = GamePage(scrumbledWord = "chrome")
        gamePage().assertInitialState()
    }

    /**
     * UGTC-07 CorrectAfterIncorrectTestCase
     */
    @Test
    fun CorrectAfterIncorrectTest() {
        gamePage.input("androit")

        gamePage.clickCheck()
        gamePage.assertIncorrectState()

        gamePage.input("androi")
        gamePage.assertInsufficientInputState()

        gamePage.input("android")
        gamePage.clickCheck()
        gamePage.assertCorrectState()

        gamePage.clickNext()
        gamePage = GamePage(scrumbledWord = "chrome")
        gamePage.initialState()
    }
}