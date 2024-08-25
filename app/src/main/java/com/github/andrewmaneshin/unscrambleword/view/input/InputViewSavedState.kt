package com.github.andrewmaneshin.unscrambleword.view.input

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class InputViewSavedState : View.BaseSavedState {

    private lateinit var state: InputUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(
                InputUiState::class.java.classLoader,
                InputUiState::class.java
            ) as InputUiState
        } else {
            parcelIn.readSerializable() as InputUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): InputUiState = state

    fun save(uiState: InputUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<InputViewSavedState> {
        override fun createFromParcel(parcel: Parcel): InputViewSavedState =
            InputViewSavedState(parcel)

        override fun newArray(size: Int): Array<InputViewSavedState?> =
            arrayOfNulls(size)
    }
}