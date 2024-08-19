package com.github.andrewmaneshin.unscrambleword

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

class TextInputLayoutErrorEnabledMatcher(private val expectingEnabled: Boolean) :
    BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("Error enabled doesn`t match with expected $expectingEnabled")
    }

    override fun matchesSafely(item: TextInputLayout): Boolean {
        return item.isErrorEnabled == expectingEnabled
    }
}