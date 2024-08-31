package com.github.andrewmaneshin.unscrambleword.view.stats

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.StatsUiState

class StatsTextView : androidx.appcompat.widget.AppCompatTextView, UpdateStats {

    private lateinit var state: StatsUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun updateUiState(uiState: StatsUiState) {
        state = uiState
        uiState.show(this)
    }

    override fun updateProperties(corrects: Int, incorrects: Int) {
        text = resources.getString(R.string.game_over, corrects, incorrects)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = StatsUiSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as StatsUiSavedState
        super.onRestoreInstanceState(restoredState.superState)
        updateUiState(restoredState.restore())
    }
}

interface UpdateStats {

    fun updateUiState(uiState: StatsUiState)

    fun updateProperties(corrects: Int, incorrects: Int)
}