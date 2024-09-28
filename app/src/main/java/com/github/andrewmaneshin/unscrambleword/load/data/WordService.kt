package com.github.andrewmaneshin.unscrambleword.load.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordService {

    @GET("api?")
    fun words(@Query("words") words: Int = 5): Call<WordsResponse>
}

data class WordsResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val wordsList: Array<String>
)