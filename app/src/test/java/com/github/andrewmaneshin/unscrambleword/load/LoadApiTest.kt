package com.github.andrewmaneshin.unscrambleword.load

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL

class LoadApiTest {

    private val gson = Gson()

    @Test
    fun test() {
        val url =
            "https://random-word-api.vercel.app/api?words=5&letter=a&type=uppercase&alphabetize=true"
        val connection = URL(url).openConnection() as HttpURLConnection

        try {
            val data = connection.inputStream.bufferedReader().use { it.readText() }
            assertTrue(data.isNotEmpty())

            val array = gson.fromJson(data, Array<String>::class.java) ?: arrayOf()
            assertEquals(5, array.size)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
    }
}