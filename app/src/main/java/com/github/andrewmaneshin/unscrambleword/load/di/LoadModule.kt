package com.github.andrewmaneshin.unscrambleword.load.di

import com.github.andrewmaneshin.unscrambleword.RunAsync
import com.github.andrewmaneshin.unscrambleword.di.Core
import com.github.andrewmaneshin.unscrambleword.di.Module
import com.github.andrewmaneshin.unscrambleword.load.data.LoadRepository
import com.github.andrewmaneshin.unscrambleword.load.data.ParseWords
import com.github.andrewmaneshin.unscrambleword.load.data.StringCache
import com.github.andrewmaneshin.unscrambleword.load.data.WordService
import com.github.andrewmaneshin.unscrambleword.load.data.WordsResponse
import com.github.andrewmaneshin.unscrambleword.load.presentation.LoadViewModel
import com.github.andrewmaneshin.unscrambleword.load.presentation.UiObservable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoadModule(private val core: Core) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val responseDefault = WordsResponse(-1, arrayOf())
        val defaultResponse = core.gson.toJson(responseDefault)
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
            LoadRepository.Base(
                retrofit.create(WordService::class.java),
                ParseWords.Base(core.gson),
                StringCache.Base(core.sharedPreferences, "response_data", defaultResponse)
            ),
            UiObservable.Base(),
            RunAsync.Base()
        )
    }
}