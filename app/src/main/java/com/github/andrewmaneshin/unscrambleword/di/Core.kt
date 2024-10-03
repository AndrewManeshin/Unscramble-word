package com.github.andrewmaneshin.unscrambleword.di

import android.content.Context
import com.github.andrewmaneshin.unscrambleword.R
import com.google.gson.Gson

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val runUiTest: Boolean = true

    val sharedPreferences =
        context.getSharedPreferences(R.string.app_name.toString(), Context.MODE_PRIVATE)

    val gson = Gson()
}