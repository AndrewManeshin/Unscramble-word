package com.github.andrewmaneshin.unscrambleword.stats

import com.github.andrewmaneshin.unscrambleword.view.stats.UpdateStats
import java.io.Serializable

interface StatsUiState : Serializable {

    fun show(statsTextView: UpdateStats)

    class Base(
        private val corrects: Int,
        private val incorrects: Int
    ) : StatsUiState {

        override fun show(statsTextView: UpdateStats) {
            statsTextView.updateProperties(corrects, incorrects)
        }
    }
}