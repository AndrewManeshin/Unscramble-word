package com.github.andrewmaneshin.unscrambleword.di

import android.content.Context
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.RunAsync
import com.github.andrewmaneshin.unscrambleword.load.data.cache.CacheModule

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val size: Int = 5
    val runAsync: RunAsync = RunAsync.Base()
    val runUiTest: Boolean = false

    val sharedPreferences =
        context.getSharedPreferences(R.string.app_name.toString(), Context.MODE_PRIVATE)

    val cacheModule = CacheModule.Base(context)
}