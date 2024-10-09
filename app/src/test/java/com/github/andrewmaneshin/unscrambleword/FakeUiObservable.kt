package com.github.andrewmaneshin.unscrambleword

import com.github.andrewmaneshin.unscrambleword.load.presentation.UiObservable

interface FakeUiObservable<UiState : Any> : UiObservable<UiState> {

    var registerCalledCount: Int
    var unregisterCalledCount: Int
    val postUiStateCalledList: MutableList<UiState>

    abstract class Abstract<UiState : Any> : FakeUiObservable<UiState> {

        private var uiStateCached: UiState? = null
        private var observerCached: ((UiState) -> Unit)? = null

        override var registerCalledCount = 0
        override var unregisterCalledCount = 0
        override val postUiStateCalledList = mutableListOf<UiState>()

        override fun register(observer: (UiState) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override fun unregister() {
            unregisterCalledCount++
            observerCached = null
        }

        override fun postUiState(uiState: UiState) {
            postUiStateCalledList.add(uiState)
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}