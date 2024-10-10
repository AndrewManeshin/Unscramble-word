package com.github.andrewmaneshin.unscrambleword.di

import android.content.Context
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.core.RunAsync
import com.github.andrewmaneshin.unscrambleword.load.data.cache.CacheModule

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val runUiTest: Boolean = true
    val size: Int = 5
    val sharedPreferences =
        context.getSharedPreferences(R.string.app_name.toString(), Context.MODE_PRIVATE)
    val correctsCache = IntCache.Base(sharedPreferences, "corrects", 0)
    val incorrectsCache = IntCache.Base(sharedPreferences, "incorrects", 0)
    val runAsync: RunAsync = RunAsync.Base()

    val cacheModule = CacheModule.Base(context)
}