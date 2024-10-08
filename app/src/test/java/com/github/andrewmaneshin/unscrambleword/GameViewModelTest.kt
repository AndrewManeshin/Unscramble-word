package com.github.andrewmaneshin.unscrambleword

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeGameRepository())
    }

    /**
     * UGTC-01 SkipTestCase
     */
    @Test
    fun skipTest() {
        val actual: GameUiState = viewModel.skip()
        val expected: GameUiState = GameUiState.Initial(scrambledWord = "poleved")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-02 InsufficientInputTestCase
     */
    @Test
    fun InsufficientInputTest() {
        var actual: GameUiState = viewModel.handleUserInput(text = "androi")
        var expected: GameUiState = GameUiState.Insufficient(scrambledWord = "diordna")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "androidd")
        expected = GameUiState.Insufficient(scrambledWord = "diordna")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        val actual: GameUiState = viewModel.handleUserInput(text = "androit")
        val expected: GameUiState = GameUiState.Sufficient(scrambledWord = "diordna")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-04 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        val actual: GameUiState = viewModel.check(text = "androit")
        val expected: GameUiState = GameUiState.Incorrect(scrambledWord = "diordna")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-05 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        var actual: GameUiState = viewModel.check(text = "androit")
        var expected: GameUiState = GameUiState.Incorrect(scrambledWord = "diordna")
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.Initial(scrambledWord = "poleved")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-06 CorrectAfterIncorrectTestCase
     */
    @Test
    fun CorrectAfterIncorrectTest() {
        var actual: GameUiState = viewModel.check(text = "androit")
        var expected: GameUiState = GameUiState.Incorrect(scrambledWord = "diordna")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "androit")
        expected = GameUiState.Sufficient(scrambledWord = "diordna")
        assertEquals(expected, actual)

        actual = viewModel.check("android")
        expected = GameUiState.Correct(scrambledWord = "diordna")
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
        if (index == originalList.size - 1) index = 0 else ++index
    }
}