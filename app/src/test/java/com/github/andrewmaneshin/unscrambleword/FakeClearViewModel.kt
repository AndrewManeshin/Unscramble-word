package com.github.andrewmaneshin.unscrambleword

import com.github.andrewmaneshin.unscrambleword.di.ClearViewModel

class FakeClearViewModel : ClearViewModel {
    override fun clear(clasz: Class<out MyViewModel>) = Unit
}