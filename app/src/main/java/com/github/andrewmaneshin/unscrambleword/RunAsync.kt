package com.github.andrewmaneshin.unscrambleword

import android.os.Handler
import android.os.Looper

interface RunAsync {

    fun <T : Any> handleAsync(heavyOperation: () -> T, uiUpdate: (T) -> Unit)

    class Base : RunAsync {

        override fun <T : Any> handleAsync(heavyOperation: () -> T, uiUpdate: (T) -> Unit) {
            Thread {
                val result = heavyOperation.invoke()
                Handler(Looper.getMainLooper()).post {
                    uiUpdate.invoke(result)
                }
            }.start()
        }
    }
}