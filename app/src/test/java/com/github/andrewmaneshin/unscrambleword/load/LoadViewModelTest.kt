package com.github.andrewmaneshin.unscrambleword.load

import org.junit.Assert.assertEquals
import org.junit.Test

class LoadViewModelTest {

    @Test
    fun sameFragment() {
        val repository = FakeLoadRepository()
        repository.expectResult(LoadResult.Success)
        val observable = FakeUiObservable()
        val viewModel = LoadViewModel(
            repository = repository,
            observable = observable
        )
        val fragment = FakeFragment()

        repository.expectResult(LoadResult.Success)
        viewModel.load(isFirstRun = true)
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment) //onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        repository.returnResult()
        assertEquals(LoadUiState.Success, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(LoadUiState.Success, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
    }

    @Test
    fun recreateActivity() {
        val repository = FakeLoadRepository()
        repository.expectResult(LoadResult.Error(message = "no internet"))
        val observable = FakeUiObservable()
        val viewModel = LoadViewModel(
            repository = repository,
            observable = observable
        )
        val fragment = FakeFragment()

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

        repository.returnResult()
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
        assertEquals(3, observable.postUiStateCalledList.size)
        assertEquals(2, repository.loadCalledCount)
        assertEquals(LoadUiState.Success, newInstanceFragment.statesList[1])
        assertEquals(2, newInstanceFragment.statesList.size)
    }
}

private class FakeFragment : (LoadUiState) -> Unit {
    val statesList = mutableListOf < LoadUiState()

    override fun invoke(p1: LoadUiState) {
        statesList.add(p1)
    }
}

private class FakeLoadRepository : LoadRepository {

    var loadCalledCount = 0
    private var loadResult: LoadResult? = null
    private var loadResultCallback: (LoadResult) -> Unit = {}

    fun expectResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    override fun load(resultCallback: (LoadResult) -> Unit) {
        loadCalledCount++
        resultCallback.invoke(loadResult)
    }

    fun returnResult() {
        loadResultCallback.invoke(loadResult)
    }
}

private class FakeUiObservable : UiObservable {

    private var uiStateCached: LoadUiState? = null
    private var observerCached: ((LoadUiState) -> Unit)? = null

    var registerCalledCount = 0

    override fun register(observer: (LoadUiState) -> Unit) {
        registerCalledCount++
        observerCached = observer
        if (uiStateCached != null) {
            observerCached!!.invoke(uiStateCached)
            uiStateCached = null
        }
    }

    var unregisterCalledCount = 0

    override fun unregister() {
        unregisterCalledCount++
        observerCached = null
    }

    val postUiStateCalledList = mutableListOf<LoadUiState>()

    override fun postUiSate(uiState: LoadUiState) {
        postUiStateCalledList.add(uiState)
        if (observerCached == null) {
            uiStateCached = uiState
        } else {
            observerCached!!.invoke(uiState)
            uiStateCached = null
        }
    }
}