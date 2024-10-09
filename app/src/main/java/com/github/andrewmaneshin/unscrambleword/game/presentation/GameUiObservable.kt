package com.github.andrewmaneshin.unscrambleword.game.presentation

import com.github.andrewmaneshin.unscrambleword.load.presentation.UiObservable

interface GameUiObservable : UiObservable<GameUiState> {

    class Base : UiObservable.Abstract<GameUiState>(), GameUiObservable
}