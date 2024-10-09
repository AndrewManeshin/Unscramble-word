package com.github.andrewmaneshin.unscrambleword.load

import com.github.andrewmaneshin.unscrambleword.FakeClearViewModel
import com.github.andrewmaneshin.unscrambleword.FakeRunAsync
import com.github.andrewmaneshin.unscrambleword.FakeUiObservable
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository
import com.github.andrewmaneshin.unscrambleword.load.data.cloud.LoadResult
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadUiObservable
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadUiState
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {

    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable
    private lateinit var runAsync: FakeRunAsync
    private lateinit var clearViewModel: FakeClearViewModel
    private lateinit var viewModel: LoadViewModel
    private lateinit var fragment: FakeFragment

    @Before
    fun setUp() {
        repository = FakeLoadRepository()
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable.Base()
        runAsync = FakeRunAsync()
        clearViewModel = FakeClearViewModel()
        viewModel = LoadViewModel(
            repository = repository,
            uiObservable = observable,
            runAsync = runAsync,
            clearViewModel = clearViewModel,
            2
        )
        fragment = FakeFragment()
    }

    @Test
    fun sameFragment() {
        viewModel.load(isFirstRun = true)
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment) //onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        runAsync.returnResult()
        assertEquals(LoadUiState.Success, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(LoadUiState.Success, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
        clearViewModel.assertActualCalled(LoadViewModel::class.java)
    }

    @Test
    fun recreateActivity() {
        repository.expectResult(LoadResult.Error(message = "no internet"))

        viewModel.load(isFirstRun = true) //onViewCreated
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment) //onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        viewModel.stopUpdates() //onPause and activity death (onStop, onDestroy)
        assertEquals(1, observable.unregisterCalledCount)

        runAsync.returnResult()
        assertEquals(
            LoadUiState.Error(message = "no internet"),
            observable.postUiStateCalledList[1]
        )
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(1, fragment.statesList.size)

        val newInstanceFragment = FakeFragment() //new instance of Fragment after activity recreate

        viewModel.load(isFirstRun = false) //onViewCreated after activity recreate
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = newInstanceFragment) //onResume after recreate
        assertEquals(2, observable.registerCalledCount)

        assertEquals(
            LoadUiState.Error(message = "no internet"),
            newInstanceFragment.statesList.first()
        )
        assertEquals(1, newInstanceFragment.statesList.size)

        repository.expectResult(LoadResult.Success)
        viewModel.load()

        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList[2])
        assertEquals(3, observable.postUiStateCalledList.size)
        assertEquals(2, repository.loadCalledCount)

        assertEquals(LoadUiState.Progress, newInstanceFragment.statesList[1])
        assertEquals(2, newInstanceFragment.statesList.size)

        runAsync.returnResult()
        assertEquals(LoadUiState.Success, observable.postUiStateCalledList[3])
        assertEquals(4, observable.postUiStateCalledList.size)
        assertEquals(LoadUiState.Success, newInstanceFragment.statesList[2])
        assertEquals(3, newInstanceFragment.statesList.size)
        clearViewModel.assertActualCalled(LoadViewModel::class.java)
    }
}

private class FakeFragment : (LoadUiState) -> Unit {
    val statesList = mutableListOf<LoadUiState>()

    override fun invoke(p1: LoadUiState) {
        statesList.add(p1)
    }
}

private class FakeLoadRepository : LoadRepository {

    var loadCalledCount = 0
    private var loadResult: LoadResult = LoadResult.Success

    fun expectResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    override suspend fun load(size: Int): LoadResult {
        loadCalledCount++
        return loadResult
    }
}

private interface FakeLoadUiObservable : FakeUiObservable<LoadUiState>, LoadUiObservable {
    class Base : FakeUiObservable.Abstract<LoadUiState>(), FakeLoadUiObservable
}