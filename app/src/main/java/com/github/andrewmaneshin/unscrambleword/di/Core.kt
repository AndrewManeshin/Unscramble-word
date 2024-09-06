package com.github.andrewmaneshin.unscrambleword.di

import android.content.Context
import com.github.andrewmaneshin.unscrambleword.R

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val sharedPreferences =
        context.getSharedPreferences(R.string.app_name.toString(), Context.MODE_PRIVATE)
}