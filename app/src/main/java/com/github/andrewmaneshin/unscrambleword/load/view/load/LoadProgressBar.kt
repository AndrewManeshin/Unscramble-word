package com.github.andrewmaneshin.unscrambleword.load.view.load

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ProgressBar
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.UpdateVisibility
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.VisibilitySavedState
import com.github.andrewmaneshin.unscrambleword.view.visibilitybutton.VisibilityUiState

class LoadProgressBar : ProgressBar, UpdateVisibility {

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
            val savedState = VisibilitySavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as VisibilitySavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(visibility: Int) {
        this.visibility = visibility
    }

    override fun update(state: VisibilityUiState) {
        this.state = state
        state.update(this)
    }
}