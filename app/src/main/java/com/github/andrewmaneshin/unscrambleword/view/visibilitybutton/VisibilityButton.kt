package com.github.andrewmaneshin.unscrambleword.view.visibilitybutton

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet

class VisibilityButton : androidx.appcompat.widget.AppCompatButton, UpdateVisibility {

    private lateinit var state: VisibilityUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = VisibilityButtonSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as VisibilityButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(visible: Int) {
        visibility = visible
    }

    override fun update(state: VisibilityUiState) {
        this.state = state
        state.update(this)
    }
}

interface UpdateVisibility {

    fun update(state: VisibilityUiState)

    fun update(visible: Int)
}