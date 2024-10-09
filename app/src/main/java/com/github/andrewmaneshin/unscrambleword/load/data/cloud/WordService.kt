package com.github.andrewmaneshin.unscrambleword.load.data.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordService {

    @GET("api?")
    fun words(@Query("words") words: Int): Call<WordsResponse>
}