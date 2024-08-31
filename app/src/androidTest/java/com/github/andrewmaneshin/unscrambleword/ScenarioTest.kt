package com.github.andrewmaneshin.unscrambleword

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.andrewmaneshin.unscrambleword.game.GamePage
import com.github.andrewmaneshin.unscrambleword.game_over.GameOverPage
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
        gamePage = GamePage(scrambledWord = "diordna")
        InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(
            R.string.app_name.toString(),
            Context.MODE_PRIVATE
        ).edit().clear().apply()
    }

    /**
     * UGTC-01 SkipTestCase
     */
    @Test
    fun skipTest() {
        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "poleved")
        doWithRecreate { gamePage.assertInitialState() }
    }

    /**
     * UGTC-02 InsufficientInputTestCase
     */
    @Test
    fun InsufficientInputTest() {
        gamePage.addInput("adnroi")
        doWithRecreate { gamePage.assertInsufficientInputState() }
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        gamePage.addInput("androit")
        doWithRecreate { gamePage.assertSufficientInputState() }
    }

    /**
     * UGTC-04 Sufficient and Insufficient InputTestCase
     */
    @Test
    fun SufficientAndInsufficientInputTest() {
        gamePage.addInput("adnroi")
        doWithRecreate { gamePage.assertInsufficientInputState() }

        gamePage.addInput("dd")
        doWithRecreate { gamePage.assertInsufficientInputState() }

        gamePage.removeInputLastLetter()
        doWithRecreate { gamePage.assertSufficientInputState() }

        gamePage.removeInputLastLetter()
        doWithRecreate { gamePage.assertInsufficientInputState() }
    }

    /**
     * UGTC-05 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        gamePage.addInput("androit")

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertIncorrectState() }
    }

    /**
     * UGTC-06 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        gamePage.addInput("androit")

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertIncorrectState() }

        gamePage.clickSkip()
        gamePage = GamePage(scrambledWord = "poleved")
        doWithRecreate { gamePage.assertInitialState() }
    }

    /**
     * UGTC-07 CorrectAfterIncorrectTestCase
     */
    @Test
    fun CorrectAfterIncorrectTest() {
        gamePage.addInput("androit")

        gamePage.clickCheck()
        doWithRecreate { gamePage.assertIncorrectState() }

        gamePage.removeInputLastLetter()
        doWithRecreate { gamePage.assertInsufficientInputState() }

        gamePage.addInput("d")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertCorrectState() }

        gamePage.clickNext()
        gamePage = GamePage(scrambledWord = "poleved")
        doWithRecreate { gamePage.assertInitialState() }
    }

    /**
     * UGTC-08 Game-over statistic
     */
    @Test
    fun StatsTest() {
        //region 2 correct and 0 incorrect
        gamePage.addInput("android")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertCorrectState() }
        gamePage.clickNext()
        doWithRecreate { gamePage.assertInitialState() }
        gamePage.addInput("develop")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertCorrectState() }
        gamePage.clickNext()
        doWithRecreate { gamePage.assertNotVisible() }

        var gameOverPage = GameOverPage(corrects = 2, incorrects = 0)
        doWithRecreate { gameOverPage.assertInitialState() }

        gameOverPage.clickNewGame()
        doWithRecreate { gameOverPage.assertNotVisible() }
        //endregion

        //region 1 correct and 1 incorrect
        gamePage.addInput("android")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertCorrectState() }
        gamePage.clickNext()
        doWithRecreate { gamePage.assertInitialState() }
        gamePage.addInput("develot")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertIncorrectState() }
        gamePage.clickSkip()
        doWithRecreate { gamePage.assertNotVisible() }

        gameOverPage = GameOverPage(corrects = 1, incorrects = 1)
        doWithRecreate { gameOverPage.assertInitialState() }

        gameOverPage.clickNewGame()
        doWithRecreate { gameOverPage.assertNotVisible() }
        //endregion

        //region 0 correct and 2 incorrect
        gamePage.addInput("androit")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertIncorrectState() }
        gamePage.clickSkip()
        doWithRecreate { gamePage.assertInitialState() }
        gamePage.addInput("develot")
        gamePage.clickCheck()
        doWithRecreate { gamePage.assertIncorrectState() }
        gamePage.clickSkip()
        doWithRecreate { gamePage.assertNotVisible() }

        gameOverPage = GameOverPage(corrects = 0, incorrects = 2)
        doWithRecreate { gameOverPage.assertInitialState() }

        gameOverPage.clickNewGame()
        doWithRecreate { gameOverPage.assertNotVisible() }
        //endregion
    }

    private fun doWithRecreate(assert: () -> Unit) {
        assert.invoke()
        scenarioRule.scenario.recreate()
        assert.invoke()
    }
}