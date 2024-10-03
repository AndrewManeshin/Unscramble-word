package com.github.andrewmaneshin.unscrambleword

import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel
import org.junit.Assert.assertEquals

class FakeClearViewModel : ClearViewModel {

    private var actual: Class<out MyViewModel>? = null

    override fun clear(clasz: Class<out MyViewModel>) {
        actual = clasz
    }

    fun assertActualCalled(expected: Class<out MyViewModel>) {
        assertEquals(expected, actual)
    }
}