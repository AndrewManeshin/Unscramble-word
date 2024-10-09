package com.github.andrewmaneshin.unscrambleword.load.presentation

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}
