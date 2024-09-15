package com.github.andrewmaneshin.unscrambleword.load

interface UiObservable {

    fun register(observer: (LoadUiState) -> Unit)

    fun unregister()

    fun postUiSate(uiState: LoadUiState)

    class Base : UiObservable {

        private var uiStateCached: LoadUiState? = null
        private var observerCached: ((LoadUiState) -> Unit)? = null

        override fun register(observer: (LoadUiState) -> Unit) {
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached)
                uiStateCached = null
            }
        }

        override fun unregister() {
            observerCached = null
        }

        override fun postUiSate(uiState: LoadUiState) {
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}