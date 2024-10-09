package com.github.andrewmaneshin.unscrambleword

import com.github.andrewmaneshin.unscrambleword.load.presentation.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface MyViewModel {

    interface Async<T : Any> : MyViewModel {

        fun startUpdates(observer: (T) -> Unit)

        fun stopUpdates()
    }

    abstract class Abstract<T : Any>(
        protected val observable: UiObservable<T>
    ) : Async<T> {

        protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        override fun startUpdates(observer: (T) -> Unit) = observable.register(observer)

        override fun stopUpdates() = observable.unregister()
    }
}