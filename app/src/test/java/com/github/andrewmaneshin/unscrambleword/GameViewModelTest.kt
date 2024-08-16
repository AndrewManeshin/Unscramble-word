package com.github.andrewmaneshin.unscrambleword

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeGameRepository)
    }

    /**
     * UGTC-01 SkipTestCase
     */
    @Test
    fun skipTest() {
        val actual: GameUiState = viewModel.skip()
        val expected: GameUiState = GameUiState.Initial(
            scrambledWord = "android"
        )
        assertEquals(expected, actual)
    }

    /**
     * UGTC-02 InsufficientInputTestCase
     */
    @Test
    fun InsufficientInputTest() {
        val actual: GameUiState = viewModel.handleUserInput(text = "androi")
        val expected: GameUiState = GameUiState.Insufficient(
            scrambledWord = "android"
        )
        assertEquals(expected, actual)

        val actual: GameUiState = viewModel.handleUserInput(text = "androidd")
        val expected: GameUiState = GameUiState.Insufficient(
            scrambledWord = "android"
        )
        assertEquals(expected, actual)
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        val actual: GameUiState = viewModel.handleUserInput(text = "androit")
        val expected: GameUiState = GameUiState.Sufficient(
            scrambledWord = "android"
        )
        assertEquals(expected, actual)
    }

    /**
     * UGTC-04 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        val actual: GameUiState = viewModel.check(text = "androit")
        val expected: GameUiState = GameUiState.Incorrect(
            scrambledWord = "android"
        )
        assertEquals(expected, actual)
    }

    /**
     * UGTC-05 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        val actual: GameUiState = viewModel.check(text = "androit")
        val expected: GameUiState = GameUiState.Incorrect(scrambledWord = "android")
        assertEquals(expected, actual)

        val actual: GameUiState = viewModel.skip
        val expected: GameUiState = GameUiState.Initial(scrambledWord = "develop")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-06 CorrectAfterIncorrectTestCase
     */
    @Test
    fun CorrectAfterIncorrectTest() {
        val actual: GameUiState = viewModel.check(text = "androit")
        val expected: GameUiState = GameUiState.Incorrect(scrambledWord = "android")
        assertEquals(expected, actual)

        val actual: GameUiState = viewModel.handleUserInput(text = "androit")
        val expected: GameUiState = GameUiState.Sufficient(scrambledWord = "android")

        val actual: GameUiState = viewModel.check("android")
        val expected: GameUiState = GameUiState.Correct(scrambledWord = "android")
        assertEquals(expected, actual)
    }
}

private class FakeGameRepository : GameRepository {

    private var index = 0

    private val originalList = listOf("android", "develop")
    private val scrambledList = originalList.map { it.reversed() }

    override fun scrambledWord(): String = scrambledList[index]

    override fun originalWord(): String = originalList[index]

    override fun next() {
        if (index == originalList.size) index = 0
        ++index
    }

    override fun check(text: String): Boolean = text == originalList[index]
}