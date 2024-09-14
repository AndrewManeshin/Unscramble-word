package com.github.andrewmaneshin.unscrambleword.game.view.check

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class CheckButton : AppCompatButton, UpdateCheckButton {

    private lateinit var state: CheckUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = CheckButtonSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CheckButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: CheckUiState) {
        this.state = uiState
        state.update(this)
    }

    override fun update(visibility: Int, enabled: Boolean) {
        this.visibility = visibility
        isEnabled = enabled
    }
}

interface UpdateCheckButton {

    fun update(uiState: CheckUiState)

    fun update(visibility: Int, enabled: Boolean)
}