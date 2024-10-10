package com.github.andrewmaneshin.unscrambleword.load.di

import com.github.andrewmaneshin.unscrambleword.core.IntCache
import com.github.andrewmaneshin.unscrambleword.core.RunAsync
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.game.data.ShuffleStrategy
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository
import com.github.andrewmaneshin.unscrambleword.load.data.cloud.WordService
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadUiObservable
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoadModule(private val core: Core) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ao0ixd.buildship.run/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return LoadViewModel(
            if (core.runUiTest)
                LoadRepository.Fake()
            else
                LoadRepository.Base(
                    retrofit.create(WordService::class.java),
                    core.cacheModule.dao(),
                    IntCache.Base(core.sharedPreferences, "index", 0),
                    ShuffleStrategy.Base
                ),
            LoadUiObservable.Base(),
            RunAsync.Base(),
            core.clearViewModel,
            core.size
        )
    }
}