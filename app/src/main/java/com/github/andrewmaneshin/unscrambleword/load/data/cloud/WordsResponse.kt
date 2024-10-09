package com.github.andrewmaneshin.unscrambleword.load.data.cloud

import com.google.gson.annotations.SerializedName

data class WordsResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val wordsList: Array<String>
)