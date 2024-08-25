package com.github.andrewmaneshin.unscrambleword.view.check

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class CheckButtonSavedState : View.BaseSavedState {

    private lateinit var state: CheckUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(
                CheckUiState::class.java.classLoader,
                CheckUiState::class.java
            ) as CheckUiState
        } else {
            parcelIn.readSerializable() as CheckUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): CheckUiState = state

    fun save(uiState: CheckUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CheckButtonSavedState> {
        override fun createFromParcel(parcel: Parcel): CheckButtonSavedState =
            CheckButtonSavedState(parcel)

        override fun newArray(size: Int): Array<CheckButtonSavedState?> =
            arrayOfNulls(size)
    }
}