package com.github.andrewmaneshin.unscrambleword.game

import com.github.andrewmaneshin.unscrambleword.MyViewModel
import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel =
            GameViewModel(repository = FakeGameRepository(), clearViewModel = FakeClearViewModel())
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
        var expected: GameUiState = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "androidd")
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        val actual: GameUiState = viewModel.handleUserInput(text = "androit")
        val expected: GameUiState = GameUiState.Sufficient
        assertEquals(expected, actual)
    }

    /**
     * UGTC-04 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        val actual: GameUiState = viewModel.check(text = "androit")
        val expected: GameUiState = GameUiState.Incorrect
        assertEquals(expected, actual)
    }

    /**
     * UGTC-05 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        var actual: GameUiState = viewModel.check(text = "androit")
        var expected: GameUiState = GameUiState.Incorrect
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
        var expected: GameUiState = GameUiState.Incorrect
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "androit")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.check("android")
        expected = GameUiState.Correct
        assertEquals(expected, actual)
    }

    /**
     * UGTC-07 Next
     */
    @Test
    fun NextTest() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial("diordna")
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial("poleved")
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Finish
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial("diordna")
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
        if (index == originalList.size) index = 0 else ++index
    }

    override fun check(text: String): Boolean {
        return if (originalWord().equals(text, true)) {
            true
        } else {
            false
        }
    }

    override fun isLastWord(): Boolean {
        return index == originalList.size
    }

    override fun clear() = Unit
}

private class FakeClearViewModel : ClearViewModel {
    override fun clear(clasz: Class<out MyViewModel>) = Unit
}