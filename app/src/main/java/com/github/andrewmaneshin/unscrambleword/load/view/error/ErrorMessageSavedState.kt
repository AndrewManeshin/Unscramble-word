package com.github.andrewmaneshin.unscrambleword.load.view.error

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class ErrorMessageSavedState : View.BaseSavedState {

    private lateinit var state: ErrorMessageUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(
                ErrorMessageUiState::class.java.classLoader,
                ErrorMessageUiState::class.java
            ) as ErrorMessageUiState
        } else {
            parcelIn.readSerializable() as ErrorMessageUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): ErrorMessageUiState = state

    fun save(uiState: ErrorMessageUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ErrorMessageSavedState> {
        override fun createFromParcel(parcel: Parcel): ErrorMessageSavedState =
            ErrorMessageSavedState(parcel)

        override fun newArray(size: Int): Array<ErrorMessageSavedState?> =
            arrayOfNulls(size)
    }
}