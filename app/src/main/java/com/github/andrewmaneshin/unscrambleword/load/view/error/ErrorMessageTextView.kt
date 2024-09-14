package com.github.andrewmaneshin.unscrambleword.load.view.error

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.StringRes

class ErrorMessageTextView : androidx.appcompat.widget.AppCompatTextView, UpdateErrorMessage {

    private lateinit var state: ErrorMessageUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = ErrorMessageSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as ErrorMessageSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: ErrorMessageUiState) {
        state = uiState
        state.update(this)
    }

    override fun updateMessage(textResId: Int) {
        setText(textResId)
    }

    override fun updateVisibility(visibility: Int) {
        this.visibility = visibility
    }
}

interface UpdateErrorMessage {

    fun update(uiState: ErrorMessageUiState)

    fun updateMessage(@StringRes textResId: Int)

    fun updateVisibility(visibility: Int)
}