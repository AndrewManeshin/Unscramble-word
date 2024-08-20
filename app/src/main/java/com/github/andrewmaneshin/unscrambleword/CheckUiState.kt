package com.github.andrewmaneshin.unscrambleword

import android.view.View
import android.widget.Button
import java.io.Serializable

interface CheckUiState : Serializable {

    fun update(checkButton: Button)

    abstract class Abstract(
        private val visible: Int,
        private val enabled: Boolean
    ) : CheckUiState {

        override fun update(checkButton: Button) = with(checkButton) {
            visibility = visible
            isEnabled = enabled
        }
    }

    object Disabled : Abstract(View.VISIBLE, false)
    object Enabled : Abstract(View.VISIBLE, true)
    object Invisible : Abstract(View.GONE, false)
}