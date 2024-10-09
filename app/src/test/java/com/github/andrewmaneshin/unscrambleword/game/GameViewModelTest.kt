package com.github.andrewmaneshin.unscrambleword.game

import com.github.andrewmaneshin.unscrambleword.FakeClearViewModel
import com.github.andrewmaneshin.unscrambleword.FakeRunAsync
import com.github.andrewmaneshin.unscrambleword.FakeUiObservable
import com.github.andrewmaneshin.unscrambleword.game.data.GameRepository
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameUiObservable
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameUiState
import com.github.andrewmaneshin.unscrambleword.game.presentation.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private lateinit var clearViewModel: FakeClearViewModel
    private lateinit var observable: FakeGameUiObservable
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        clearViewModel = FakeClearViewModel()
        observable = FakeGameUiObservable.Base()
        runAsync = FakeRunAsync()
        viewModel = GameViewModel(
            repository = FakeGameRepository(),
            clearViewModel = clearViewModel,
            uiObservable = observable,
            runAsync = runAsync
        )
    }

    /**
     * UGTC-01 SkipTestCase
     */
    @Test
    fun skipTest() {
        viewModel.skip()
        runAsync.returnResult()
        val actual: GameUiState = observable.postUiStateCalledList.last()
        val expected: GameUiState = GameUiState.Initial(scrambledWord = "poleved")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-02 InsufficientInputTestCase
     */
    @Test
    fun InsufficientInputTest() {
        viewModel.handleUserInput(text = "androi")
        runAsync.returnResult()
        var actual: GameUiState = observable.postUiStateCalledList.last()
        var expected: GameUiState = GameUiState.Insufficient
        assertEquals(expected, actual)

        viewModel.handleUserInput(text = "androidd")
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)
    }

    /**
     * UGTC-03 SufficientInputTestCase
     */
    @Test
    fun SufficientInputTest() {
        viewModel.handleUserInput(text = "androit")
        runAsync.returnResult()
        val actual: GameUiState = observable.postUiStateCalledList.last()
        val expected: GameUiState = GameUiState.Sufficient
        assertEquals(expected, actual)
    }

    /**
     * UGTC-04 IncorrectTestCase
     */
    @Test
    fun IncorrectTest() {
        viewModel.check(text = "androit")
        runAsync.returnResult()
        val actual: GameUiState = observable.postUiStateCalledList.last()
        val expected: GameUiState = GameUiState.Incorrect
        assertEquals(expected, actual)
    }

    /**
     * UGTC-05 SkipAfterIncorrectTestCase
     */
    @Test
    fun SkipAfterIncorrectTest() {
        viewModel.check(text = "androit")
        runAsync.returnResult()
        var actual: GameUiState = observable.postUiStateCalledList.last()
        var expected: GameUiState = GameUiState.Incorrect
        assertEquals(expected, actual)

        viewModel.skip()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Initial(scrambledWord = "poleved")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-06 CorrectAfterIncorrectTestCase
     */
    @Test
    fun CorrectAfterIncorrectTest() {
        viewModel.check(text = "androit")
        runAsync.returnResult()
        var actual: GameUiState = observable.postUiStateCalledList.last()
        var expected: GameUiState = GameUiState.Incorrect
        assertEquals(expected, actual)

        viewModel.handleUserInput(text = "androit")
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        viewModel.check("android")
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Correct
        assertEquals(expected, actual)
    }

    /**
     * UGTC-07 Next
     */
    @Test
    fun NextTest() {
        viewModel.init()
        runAsync.returnResult()
        var actual: GameUiState = observable.postUiStateCalledList.last()
        var expected: GameUiState = GameUiState.Initial("diordna")
        assertEquals(expected, actual)

        viewModel.next()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Initial("poleved")
        assertEquals(expected, actual)

        viewModel.next()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Finish
        assertEquals(expected, actual)
        clearViewModel.assertActualCalled(GameViewModel::class.java)

        viewModel.next()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = GameUiState.Initial("diordna")
        assertEquals(expected, actual)
    }
}

private interface FakeGameUiObservable : FakeUiObservable<GameUiState>, GameUiObservable {
    class Base : FakeUiObservable.Abstract<GameUiState>(), FakeGameUiObservable
}

private class FakeGameRepository : GameRepository {

    private var index = 0

    private val originalList = listOf("android", "develop")
    private val scrambledList = originalList.map { it.reversed() }

    override suspend fun scrambledWord(): String = scrambledList[index]

    override suspend fun originalWord(): String = originalList[index]

    override fun next() {
        if (index == originalList.size) index = 0 else ++index
    }

    override suspend fun check(text: String): Boolean {
        return if (originalWord().equals(text, true)) {
            true
        } else {
            false
        }
    }

    override fun isLastWord(): Boolean {
        return index == originalList.size
    }

    override suspend fun clear() = Unit
}

