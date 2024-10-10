package com.github.andrewmaneshin.unscrambleword.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.core.MyViewModel
import com.github.andrewmaneshin.unscrambleword.core.Navigate
import com.github.andrewmaneshin.unscrambleword.core.Screen
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel

class MainActivity : AppCompatActivity(), Navigate, ProvideViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val viewModel: MainViewModel = makeViewModel(MainViewModel::class.java)
        val screen = viewModel.firstScreen(savedInstanceState == null)
        navigate(screen)
    }

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>) =
        (application as ProvideViewModel).makeViewModel(clasz)

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }
}